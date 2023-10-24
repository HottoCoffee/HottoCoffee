package com.github.hottocoffee.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.inject.{Inject, Singleton}
import play.api.libs.json.{JsPath, JsValue, Json, Writes}
import play.api.mvc.{Action, BaseController, ControllerComponents}

import scala.util.chaining.*

@Singleton
class UserController @Inject()(val controllerComponents: ControllerComponents) extends BaseController:
  private val objectMapper = ObjectMapper()

  def find(id: Long): Action[_] = Action {
    id match
      case 1 => UserResponse(1, "getupmax", "hoge@example.com", "tasuku_nakagawa", "hoge", null)
        .pipe(Json.toJson)
        .pipe(Ok(_))
      case 2 => UserResponse(2, "seito", "foo@example.com", "seito_hirai", "fuga", null)
        .pipe(Json.toJson)
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
