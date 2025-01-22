package com.github.hottocoffee.controller

import com.github.hottocoffee.controller.auth.LoginRequiredAction
import com.github.hottocoffee.controller.schema.response.{UserOutput, UserRegisterInput}
import com.github.hottocoffee.dao.UserDao
import com.github.hottocoffee.util.value2Optional
import jakarta.inject.{Inject, Singleton}
import play.api.libs.json.{JsError, JsSuccess, Json}
import play.api.mvc.{Action, BaseController, ControllerComponents}

import scala.util.chaining.*

@Singleton
class UserController @Inject()(val controllerComponents: ControllerComponents,
                               private val userDao: UserDao,
                               private val loginRequiredAction: LoginRequiredAction) extends BaseController:

  def currentUser(): Action[_] = loginRequiredAction { request =>
    userDao.selectByUserId(request.user) match
      case Some(user) => UserOutput.from(user).pipe(Json.toJson).pipe(Ok(_))
      case None => NotFound
  }

  def find(accountId: String): Action[_] = Action {
    userDao.selectByAccountId(accountId) match
      case Some(user) => UserOutput.from(user).pipe(Json.toJson).pipe(Ok(_))
      case None => NotFound
  }

  def update(accountId: String): Action[_] = Action { request =>
    request.body.asJson match
      case None => UnsupportedMediaType
      case Some(json) => json.validate[UserRegisterInput] match
        case e: JsError => BadRequest
        case JsSuccess(body, _) =>
          UserOutput(
            userId = 1,
            accountId = "getupmax",
            email = "hoge@example.com",
            displayName = "tasuku_nakagawa",
            introduction = "hoge",
            iconImageKey = "https://avatars.githubusercontent.com/u/38446259?v=4",
          ).pipe(Json.toJson)
            .pipe(Ok(_))
  }
