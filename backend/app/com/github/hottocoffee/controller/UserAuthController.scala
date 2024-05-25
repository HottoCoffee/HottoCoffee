package com.github.hottocoffee.controller

import com.github.hottocoffee.controller.auth.saveUserToSession
import com.github.hottocoffee.controller.schema.response.{UserOutput, UserRegisterInput, UserSignInInput}
import com.github.hottocoffee.dao.UserDao
import com.github.hottocoffee.service.{PlainPassword, RegisterUserService}
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
          registerUserService.register(
            body.accountId,
            body.email,
            PlainPassword(body.password),
            body.displayName,
            body.introduction.orElse(""),
            body.iconUrl,
          ) match
            case Left(errorMessage) =>
              logger.warn(errorMessage)
              BadRequest
            case Right(user) => UserOutput.from(user).pipe(Json.toJson).pipe(Created(_))
  }

  def signIn(): Action[_] = Action { request =>
    request.body.asJson match
      case None => UnsupportedMediaType
      case Some(json) => json.validate[UserSignInInput] match
        case _: JsError => BadRequest
        case JsSuccess(body, _) =>
          userDao.selectByEmailAndPassword(body.email, PlainPassword(body.password)) match
            case None => BadRequest
            case Some(user) =>
              saveUserToSession(user, request.session)
              UserOutput.from(user).pipe(Json.toJson).pipe(Ok(_))
  }
