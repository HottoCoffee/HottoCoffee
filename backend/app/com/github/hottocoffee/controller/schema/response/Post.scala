package com.github.hottocoffee.controller.schema.response

import play.api.libs.functional.syntax.toFunctionalBuilderOps
import play.api.libs.json.{JsPath, JsString, Json, Writes}

case class PostOutput(postId: Int,
                      userInfo: UserInfoOutput,
                      location: String,
                      origin: String,
                      wayToBrew: String,
                      roastLevel: Option[RoastLevel],
                      temperature: Option[Int], // 0 -- 100
                      gramsOfCoffee: Option[Int],
                      gramsOfWater: Option[Int],
                      grindSize: Option[GrindSize],
                      impression: String)

enum RoastLevel:
  case LIGHT, CINNAMON, MEDIUM, HIGH, CITY, FULL_CITY, FRENCH, ITALIAN

object RoastLevel:
  implicit val writes: Writes[RoastLevel] = o => JsString(o.toString.toLowerCase)

enum GrindSize:
  case FINEST, FINE, MEDIUM_FINE, MEDIUM, COARSE

object GrindSize:
  implicit val writes: Writes[GrindSize] = o => JsString(o.toString.toLowerCase)

object PostOutput:
  implicit val writes: Writes[PostOutput] = (
    (JsPath \ "post_id").write[Int] ~
      (JsPath \ "user_info").write[UserInfoOutput] ~
      (JsPath \ "location").write[String] ~
      (JsPath \ "origin").write[String] ~
      (JsPath \ "way_to_brew").write[String] ~
      (JsPath \ "roast_level").writeNullable[RoastLevel] ~
      (JsPath \ "temperature").writeNullable[Int] ~
      (JsPath \ "grams_of_coffee").writeNullable[Int] ~
      (JsPath \ "grams_of_water").writeNullable[Int] ~
      (JsPath \ "grind_size").writeNullable[GrindSize] ~
      (JsPath \ "impression").write[String]
    )(o => (
    o.postId, o.userInfo, o.location, o.origin, o.wayToBrew, o.roastLevel, o.temperature, o.gramsOfCoffee,
    o.gramsOfWater, o.grindSize, o.impression
  ))

case class UserInfoOutput(userId: Int,
                          accountId: String,
                          displayName: String,
                          iconUrl: Option[String])

object UserInfoOutput:
  implicit val writes: Writes[UserInfoOutput] = (
    (JsPath \ "user_id").write[Int] ~
      (JsPath \ "account_id").write[String] ~
      (JsPath \ "display_name").write[String] ~
      (JsPath \ "icon_url").writeNullable[String]
    )(o => (
    o.userId, o.accountId, o.displayName, o.iconUrl
  ))
