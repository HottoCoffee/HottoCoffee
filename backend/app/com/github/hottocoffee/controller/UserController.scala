package com.github.hottocoffee.controller

import com.github.hottocoffee.controller.schema.response.{UserInput, UserOutput}
import com.github.hottocoffee.util.value2Optional
import jakarta.inject.{Inject, Singleton}
import play.api.libs.json.{JsError, JsSuccess, Json}
import play.api.mvc.{Action, BaseController, ControllerComponents}

import scala.util.chaining.*

@Singleton
class UserController @Inject()(val controllerComponents: ControllerComponents) extends BaseController:
  def find(accountId: String): Action[_] = Action {
    accountId match
      case "getupmax" => UserOutput(
        userId = 1,
        accountId = "getupmax",
        email = "hoge@example.com",
        displayName = "tasuku_nakagawa",
        introduction = Some("hoge"),
        iconUrl = Some("https://avatars.githubusercontent.com/u/38446259?v=4"),
      ).pipe(Json.toJson)
        .pipe(Ok(_))
      case "seito2" => UserOutput(
        userId = 2,
        accountId = "seito",
        email = "foo@example.com",
        displayName = "seito_hirai",
        introduction = "fuga",
        iconUrl = "https://avatars.githubusercontent.com/u/42537610?v=4",
      ).pipe(Json.toJson)
        .pipe(Ok(_))
      case _ => NotFound
  }

  def register(): Action[_] = Action { request =>
    request.body.asJson match
      case None => UnsupportedMediaType
      case Some(json) => json.validate[UserInput] match
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
            .pipe(Ok(_))
  }

  def update(accountId: String): Action[_] = Action { request =>
    request.body.asJson match
      case None => UnsupportedMediaType
      case Some(json) => json.validate[UserInput] match
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
            .pipe(Ok(_))
  }
