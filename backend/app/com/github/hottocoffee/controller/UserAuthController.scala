package com.github.hottocoffee.controller

import com.github.hottocoffee.controller.auth.saveUserToSession
import com.github.hottocoffee.controller.schema.response.{UserOutput, UserRegisterInput, UserSignInInput}
import com.github.hottocoffee.dao.UserDao
import com.github.hottocoffee.service.PlainPassword
import com.github.hottocoffee.util.{option2Nullable, value2Optional}
import jakarta.inject.{Inject, Singleton}
import play.api.libs.json.{JsError, JsSuccess, Json}
import play.api.mvc.{Action, BaseController, ControllerComponents}

import scala.util.chaining.*

@Singleton
class UserAuthController @Inject()(val controllerComponents: ControllerComponents,
                                   val userDao: UserDao) extends BaseController:
  def register(): Action[_] = Action { request =>
    request.body.asJson match
      case None => UnsupportedMediaType
      case Some(json) => json.validate[UserRegisterInput] match
        case _: JsError => BadRequest
        case JsSuccess(body, _) =>
          userDao.insert(
            body.accountId,
            body.email,
            PlainPassword(body.password),
            body.displayName,
            body.introduction,
            body.iconUrl,
          ).map(user =>
            UserOutput(
              user.id.toInt,
              user.accountId,
              user.email,
              user.displayName,
              user.introduction,
              user.iconUrl,
            ).pipe(Json.toJson)
              .pipe(Created(_))
          ).orElse(BadRequest)
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
              UserOutput(
                userId = user.id.toInt,
                accountId = user.accountId,
                email = user.email,
                displayName = user.displayName,
                introduction = user.introduction,
                iconUrl = user.iconUrl,
              ).pipe(Json.toJson)
                .pipe(Created(_))
  }
