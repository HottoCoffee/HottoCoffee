package com.github.hottocoffee.controller

import com.github.hottocoffee.controller.schema.response.{UserOutput, UserRegisterInput}
import com.github.hottocoffee.util.value2Optional
import jakarta.inject.{Inject, Singleton}
import play.api.libs.json.{JsError, JsSuccess, Json}
import play.api.mvc.{Action, BaseController, ControllerComponents}

import scala.util.chaining.*

@Singleton
class UserAuthController @Inject()(val controllerComponents: ControllerComponents) extends BaseController:
  def register(): Action[_] = Action { request =>
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
            iconUrl = "https://avatars.githubusercontent.com/u/38446259?v=4",
          ).pipe(Json.toJson)
            .pipe(Created(_))
  }

  def signIn(): Action[_] = Action {
    Ok
  }
