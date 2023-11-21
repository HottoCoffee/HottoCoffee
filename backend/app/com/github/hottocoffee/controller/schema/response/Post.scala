package com.github.hottocoffee.controller.schema.response

import play.api.libs.json.{JsString, Json, Writes}

case class Post(postId: Int,
                userInfo: UserInfo,
                location: String,
                origin: String,
                wayToBrew: String,
                roastLevel: RoastLevel | Null,
                temperature: Int | Null, // 0 -- 100
                gramsOfCoffee: Int | Null,
                gramsOfWater: Int | Null,
                grindSize: GrindSize | Null,
                impression: String)

enum RoastLevel:
  case LIGHT, CINNAMON, MEDIUM, HIGH, CITY, FULL_CITY, FRENCH, ITALIAN

object RoastLevel:
  implicit val writes: Writes[RoastLevel] = o => JsString(o.toString.toLowerCase)

enum GrindSize:
  case FINEST, FINE, MEDIUM_FINE, MEDIUM, COARSE

object GrindSize:
  implicit val writes: Writes[GrindSize] = o => JsString(o.toString.toLowerCase)

object Post:
  implicit val writes: Writes[Post] = o => Json.obj(
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

case class UserInfo(userId: Int,
                    accountId: String,
                    displayName: String,
                    iconUrl: String)

object UserInfo:
  implicit val writes: Writes[UserInfo] = o => Json.obj(
    "user_id" -> o.userId,
    "account_id" -> o.accountId,
    "display_name" -> o.displayName,
    "icon_url" -> o.iconUrl,
  )
