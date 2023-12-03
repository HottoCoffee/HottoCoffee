package com.github.hottocoffee.controller.schema.response

import play.api.libs.functional.syntax.toFunctionalBuilderOps
import play.api.libs.json.*

case class UserOutput(userId: Int,
                      accountId: String,
                      email: String,
                      displayName: String,
                      introduction: Option[String],
                      iconUrl: Option[String])

object UserOutput:
  implicit val writes: Writes[UserOutput] = (
    (JsPath \ "user_id").write[Int] ~
      (JsPath \ "account_id").write[String] ~
      (JsPath \ "email").write[String] ~
      (JsPath \ "display_name").write[String] ~
      (JsPath \ "introduction").writeNullable[String] ~
      (JsPath \ "icon_url").writeNullable[String]
    )(o => (o.userId, o.accountId, o.email, o.displayName, o.introduction, o.iconUrl))

case class UserInput(accountId: String,
                     email: String,
                     displayName: String,
                     introduction: Option[String],
                     iconUrl: Option[String])

object UserInput:
  implicit val reads: Reads[UserInput] = (
    (JsPath \ "account_id").read[String] ~
      (JsPath \ "email").read[String] ~
      (JsPath \ "display_name").read[String] ~
      (JsPath \ "introduction").readNullable[String] ~
      (JsPath \ "icon_url").readNullable[String]
    )(UserInput.apply _)