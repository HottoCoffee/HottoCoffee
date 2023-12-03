package com.github.hottocoffee.controller

import com.github.hottocoffee.controller.schema.response.User
import jakarta.inject.{Inject, Singleton}
import play.api.libs.json.Json
import play.api.mvc.{Action, BaseController, ControllerComponents}

import scala.util.chaining.*

@Singleton
class UserController @Inject()(val controllerComponents: ControllerComponents) extends BaseController:
  def find(accountId: String): Action[_] = Action {
    accountId match
      case "getupmax" => User(
        userId = 1,
        accountId = "getupmax",
        email = "hoge@example.com",
        displayName = "tasuku_nakagawa",
        introduction = "hoge",
        iconUrl = "https://avatars.githubusercontent.com/u/38446259?v=4"
      ).pipe(Json.toJson)
        .pipe(Ok(_))
      case "seito2" => User(
        userId = 2,
        accountId = "seito",
        email = "foo@example.com",
        displayName = "seito_hirai",
        introduction = "fuga",
        iconUrl = "https://avatars.githubusercontent.com/u/42537610?v=4"
      ).pipe(Json.toJson)
        .pipe(Ok(_))
      case _ => NotFound
  }
