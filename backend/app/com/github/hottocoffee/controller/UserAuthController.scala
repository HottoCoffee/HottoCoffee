package com.github.hottocoffee.controller

import com.github.hottocoffee.controller.schema.response.{UserOutput, UserRegisterInput, UserSignInInput}
import com.github.hottocoffee.dao.UserDao
import com.github.hottocoffee.model.PlainPassword
import com.github.hottocoffee.service.RegisterUserService
import com.github.hottocoffee.util.{option2Nullable, value2Optional}
import jakarta.inject.{Inject, Singleton}
import pdi.jwt.{Jwt, JwtAlgorithm, JwtClaim}
import play.api.libs.json.{JsError, JsSuccess, Json}
import play.api.mvc.{Action, BaseController, ControllerComponents}
import play.api.{Configuration, Logger}

import java.time.Clock
import scala.util.chaining.*

@Singleton
class UserAuthController @Inject()(val controllerComponents: ControllerComponents,
                                   private val registerUserService: RegisterUserService,
                                   private val userDao: UserDao,
                                   private val configuration: Configuration) extends BaseController:
  private val logger = Logger(this.getClass)

  def register(): Action[_] = Action { request =>
    request.body.asJson match
      case None => UnsupportedMediaType
      case Some(json) => json.validate[UserRegisterInput] match
        case _: JsError => BadRequest
        case JsSuccess(body, _) =>
          PlainPassword(body.password) match
            case Left(_) => BadRequest
            case Right(validPlainPassword) => registerUserService.register(
              body.accountId,
              body.email,
              validPlainPassword,
              body.displayName,
              body.introduction.orElse(""),
              body.iconUrl,
            ) match
              case Left(errorMessage) =>
                logger.warn(errorMessage)
                BadRequest
              case Right(user) =>
                UserOutput.from(user).pipe(Json.toJson)
                  .pipe(Created(_))
  }

  def signIn(): Action[_] = Action { request =>
    request.body.asJson match
      case None => UnsupportedMediaType
      case Some(json) => json.validate[UserSignInInput] match
        case _: JsError => BadRequest
        case JsSuccess(body, _) =>
          PlainPassword(body.password) match
            case Left(_) => BadRequest
            case Right(validPlainPassword) => userDao.selectByEmailAndPassword(body.email, validPlainPassword) match
              case None => BadRequest
              case Some(user) =>
                Json.obj("token" -> generateJwt(user.id))
                  .pipe(Ok(_))
  }

  private def generateJwt(userId: Long): String =
    Jwt.encode(
      JwtClaim(Json.obj("id" -> userId).toString).issuedIn(60 * 60)(Clock.systemUTC()),
      configuration.get[String]("app.jwt.key"),
      JwtAlgorithm.HS256,
    )
