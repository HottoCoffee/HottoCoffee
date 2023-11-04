package com.github.hottocoffee.controllers

import jakarta.inject.{Inject, Singleton}
import play.api.libs.json.{JsNull, JsNumber, JsString, JsValue, Json, Writes}
import play.api.mvc.{Action, BaseController, ControllerComponents}

import scala.util.chaining.*

@Singleton
class PostController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {
  def find(postId: Int): Action[_] = Action {
    postId match
      case 1 => PostSchema(
        postId = 1,
        userInfo = UserInfoSchema(
          userId = 2,
          accountId = "seito2",
          displayName = "seito_hirai",
          iconUrl = "https://avatars.githubusercontent.com/u/42537610?v=4",
        ),
        location = "home",
        origin = "Kenya",
        wayToBrew = "Latte",
        roastLevel = RoastLevel.MEDIUM,
        temperature = null,
        gramsOfCoffee = null,
        gramsOfWater = null,
        grindSize = null,
        impression = "Wow",
      ).pipe(Json.toJson)
        .pipe(Ok(_))

      case 2 => PostSchema(
        postId = 2,
        userInfo = UserInfoSchema(
          userId = 2,
          accountId = "seito2",
          displayName = "seito_hirai",
          iconUrl = "https://avatars.githubusercontent.com/u/42537610?v=4",
        ),
        location = "home",
        origin = "Kenya",
        wayToBrew = "Latte",
        roastLevel = null,
        temperature = null,
        gramsOfCoffee = null,
        gramsOfWater = null,
        grindSize = null,
        impression = null,
      ).pipe(Json.toJson)
        .pipe(Ok(_))


      case _ => NotFound
  }
}

case class PostSchema(postId: Int,
                      userInfo: UserInfoSchema,
                      location: String,
                      origin: String,
                      wayToBrew: String,
                      roastLevel: RoastLevel | Null,
                      temperature: Int | Null, // 0 -- 100
                      gramsOfCoffee: Int | Null,
                      gramsOfWater: Int | Null,
                      grindSize: GrindSize | Null,
                      impression: String)

case class UserInfoSchema(userId: Int,
                          accountId: String,
                          displayName: String,
                          iconUrl: String)

enum RoastLevel:
  case LIGHT, CINNAMON, MEDIUM, HIGH, CITY, FULL_CITY, FRENCH, ITALIAN

object RoastLevel:
  implicit val writes: Writes[RoastLevel] = o => JsString(o.toString.toLowerCase)

enum GrindSize:
  case FINEST, FINE, MEDIUM_FINE, MEDIUM, COARSE

object GrindSize:
  implicit val writes: Writes[GrindSize] = o => JsString(o.toString.toLowerCase)

object PostSchema:
  implicit val writes: Writes[PostSchema] = o => Json.obj(
    "post_id" -> o.postId,
    "user_info" -> o.userInfo,
    "location" -> o.location,
    "origin" -> o.origin,
    "way_to_brew" -> o.wayToBrew,
    "roast_level" -> o.roastLevel,
    "temperature" -> o.temperature,
    "grams_of_coffee" -> o.gramsOfCoffee,
    "grams_of_watter" -> o.gramsOfWater,
    "grind_size" -> o.grindSize,
    "impression" -> o.impression,
  )

object UserInfoSchema:
  implicit val writes: Writes[UserInfoSchema] = o => Json.obj(
    "user_id" -> o.userId,
    "account_id" -> o.accountId,
    "display_name" -> o.displayName,
    "icon_url" -> o.iconUrl,
  )

implicit def writesNullable[A](implicit writesA: Writes[A]): Writes[A | Null] = {
  case value: A => writesA.writes(value)
  case null => JsNull
}
