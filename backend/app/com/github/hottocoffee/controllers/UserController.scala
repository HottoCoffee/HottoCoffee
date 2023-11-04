package com.github.hottocoffee.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.inject.{Inject, Singleton}
import play.api.libs.json.{JsPath, JsValue, Json, Writes}
import play.api.mvc.{Action, BaseController, ControllerComponents}

import scala.util.chaining.*

@Singleton
class UserController @Inject()(val controllerComponents: ControllerComponents) extends BaseController:
  def find(accountId: String): Action[_] = Action {
    accountId match
      case "getupmax" => UserResponse(
        userId = 1,
        accountId = "getupmax",
        email = "hoge@example.com",
        displayName = "tasuku_nakagawa",
        introduction = "hoge",
        iconUrl = "https://avatars.githubusercontent.com/u/38446259?v=4"
      ).pipe(Json.toJson)
        .pipe(Ok(_))
      case "seito2" => UserResponse(
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

case class UserResponse(userId: Int,
                        accountId: String,
                        email: String,
                        displayName: String,
                        introduction: String,
                        iconUrl: String)

object UserResponse:
  implicit val writes: Writes[UserResponse] = (o: UserResponse) => Json.obj(
    "user_id" -> o.userId,
    "account_id" -> o.accountId,
    "email" -> o.email,
    "display_name" -> o.displayName,
    "introduction" -> o.introduction,
    "icon_url" -> o.iconUrl
  )
