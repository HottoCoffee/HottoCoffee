package com.github.hottocoffee.controller.schema.response

import com.github.hottocoffee.model.User
import com.github.hottocoffee.model.coffee.*
import com.github.hottocoffee.util.nullable2Optional
import play.api.libs.functional.syntax.toFunctionalBuilderOps
import play.api.libs.json.*

case class PostInput(location: Option[Location],
                     origin: CoffeeOrigin,
                     wayToBrew: Option[WayToBrew],
                     roastLevel: Option[RoastLevel],
                     temperature: Option[Temperature],
                     gramsOfCoffee: Option[GramsOfCoffee],
                     gramsOfWater: Option[GramsOfWater],
                     grindSize: Option[GrindSize],
                     impression: Option[String])

case class PostOutput(postId: Int,
                      userInfo: UserInfoOutput,
                      location: Option[Location],
                      origin: CoffeeOrigin,
                      wayToBrew: Option[WayToBrew],
                      roastLevel: Option[RoastLevel],
                      temperature: Option[Temperature],
                      gramsOfCoffee: Option[GramsOfCoffee],
                      gramsOfWater: Option[GramsOfWater],
                      grindSize: Option[GrindSize],
                      impression: Option[String])

case class UserInfoOutput(userId: Int,
                          accountId: String,
                          displayName: String,
                          iconUrl: Option[String])

implicit val locationReads: Reads[Location] = {
  case JsString(v) =>
    val tmp = Location(v)
    tmp match
      case Right(value) => JsSuccess(value)
      case _ => JsError()

  case _ => JsError()
}
implicit val locationWrites: Writes[Location] = o => JsString(o.value)

implicit val originReads: Reads[CoffeeOrigin] = {
  case JsString(v) =>
    CoffeeOrigin(v) match
      case Right(value) => JsSuccess(value)
      case _ => JsError()

  case _ => JsError()
}
implicit val originWrites: Writes[CoffeeOrigin] = o => JsString(o.value)

implicit val wayToBrewReads: Reads[WayToBrew] = {
  case JsString(v) =>
    WayToBrew(v) match
      case Right(value) => JsSuccess(value)
      case _ => JsError()

  case _ => JsError()
}
implicit val wayToBrewWrites: Writes[WayToBrew] = o => JsString(o.value)

implicit val roastLevelReads: Reads[RoastLevel] = {
  case JsString(v) =>
    RoastLevel(v) match
      case Right(value) => JsSuccess(value)
      case _ => JsError()
  case _ => JsError()
}
implicit val roastLevelWrites: Writes[RoastLevel] = o => JsString(o.value)

implicit val temperatureReads: Reads[Temperature] = {
  case JsNumber(v) =>
    Temperature(v.toInt) match
      case Right(value) => JsSuccess(value)
      case _ => JsError()

  case _ => JsError()
}
implicit val temperatureWrites: Writes[Temperature] = o => JsNumber(o.value)

implicit val gramsOfCoffeeReads: Reads[GramsOfCoffee] = {
  case JsNumber(v) =>
    GramsOfCoffee(v.toInt) match
      case Right(value) => JsSuccess(value)
      case _ => JsError()

  case _ => JsError()
}
implicit val gramsOfCoffeeWrites: Writes[GramsOfCoffee] = o => JsNumber(o.value)

implicit val gramsOfWaterReads: Reads[GramsOfWater] = {
  case JsNumber(v) =>
    GramsOfWater(v.toInt) match
      case Right(value) => JsSuccess(value)
      case _ => JsError()

  case _ => JsError()
}
implicit val gramsOfWaterWrites: Writes[GramsOfWater] = o => JsNumber(o.value)

implicit val grindSizeReads: Reads[GrindSize] = {
  case JsString(v) =>
    GrindSize(v) match
      case Right(value) => JsSuccess(value)
      case _ => JsError()
  case a =>
    JsError()
}
implicit val grindSizeWrites: Writes[GrindSize] = o => JsString(o.value)

object PostInput:
  implicit val reads: Reads[PostInput] = (
    (__ \ "location").readNullable[Location] ~
      (__ \ "origin").read[CoffeeOrigin] ~
      (__ \ "way_to_brew").readNullable[WayToBrew] ~
      (__ \ "roast_level").readNullable[RoastLevel] ~
      (__ \ "temperature").readNullable[Temperature] ~
      (__ \ "grams_of_coffee").readNullable[GramsOfCoffee] ~
      (__ \ "grams_of_water").readNullable[GramsOfWater] ~
      (__ \ "grind_size").readNullable[GrindSize] ~
      (__ \ "impression").readNullable[String]
    )(PostInput.apply _)

object PostOutput:
  implicit val writes: Writes[PostOutput] = (
    (JsPath \ "post_id").write[Int] ~
      (JsPath \ "user_info").write[UserInfoOutput] ~
      (JsPath \ "location").writeNullable[Location] ~
      (JsPath \ "origin").write[CoffeeOrigin] ~
      (JsPath \ "way_to_brew").writeNullable[WayToBrew] ~
      (JsPath \ "roast_level").writeNullable[RoastLevel] ~
      (JsPath \ "temperature").writeNullable[Temperature] ~
      (JsPath \ "grams_of_coffee").writeNullable[GramsOfCoffee] ~
      (JsPath \ "grams_of_water").writeNullable[GramsOfWater] ~
      (JsPath \ "grind_size").writeNullable[GrindSize] ~
      (JsPath \ "impression").writeNullable[String]
    )(o => (
    o.postId, o.userInfo, o.location, o.origin, o.wayToBrew, o.roastLevel, o.temperature, o.gramsOfCoffee,
    o.gramsOfWater, o.grindSize, o.impression
  ))

object UserInfoOutput:
  implicit val writes: Writes[UserInfoOutput] = (
    (JsPath \ "user_id").write[Int] ~
      (JsPath \ "account_id").write[String] ~
      (JsPath \ "display_name").write[String] ~
      (JsPath \ "icon_image_key").writeNullable[String]
    )(o => (
    o.userId, o.accountId, o.displayName, o.iconUrl
  ))

  def from(user: User): UserInfoOutput = UserInfoOutput(
    user.id.toInt,
    user.accountId,
    user.displayName,
    user.iconImageKey,
  )
