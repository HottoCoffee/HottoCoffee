package com.github.hottocoffee.controller

import com.github.hottocoffee.controller.auth.appendUserSession
import com.github.hottocoffee.controller.schema.response.{UserOutput, UserRegisterInput, UserSignInInput}
import com.github.hottocoffee.dao.UserDao
import com.github.hottocoffee.model.PlainPassword
import com.github.hottocoffee.service.RegisterUserService
import com.github.hottocoffee.util.{option2Nullable, value2Optional}
import jakarta.inject.{Inject, Singleton}
import play.api.Logger
import play.api.libs.json.{JsError, JsSuccess, Json}
import play.api.mvc.{Action, BaseController, ControllerComponents}

import scala.util.chaining.*

@Singleton
class UserAuthController @Inject()(val controllerComponents: ControllerComponents,
                                   private val registerUserService: RegisterUserService,
                                   private val userDao: UserDao) extends BaseController:
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
                  .pipe(Created(_).appendUserSession(user, request.session))
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
                UserOutput.from(user).pipe(Json.toJson)
                  .pipe(Ok(_).appendUserSession(user, request.session))
  }
