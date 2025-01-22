package com.github.hottocoffee.controller.schema.response

import com.github.hottocoffee.model.User
import com.github.hottocoffee.util.nullable2Optional
import play.api.libs.functional.syntax.toFunctionalBuilderOps
import play.api.libs.json.*

case class UserOutput(userId: Int,
                      accountId: String,
                      email: String,
                      displayName: String,
                      introduction: Option[String],
                      iconImageKey: Option[String])

object UserOutput:
  implicit val writes: Writes[UserOutput] = (
    (JsPath \ "user_id").write[Int] ~
      (JsPath \ "account_id").write[String] ~
      (JsPath \ "email").write[String] ~
      (JsPath \ "display_name").write[String] ~
      (JsPath \ "introduction").writeNullable[String] ~
      (JsPath \ "icon_image_key").writeNullable[String]
    )(o => (o.userId, o.accountId, o.email, o.displayName, o.introduction, o.iconImageKey))

  def from(user: User): UserOutput = UserOutput(
    user.id.toInt,
    user.accountId,
    user.email,
    user.displayName,
    Some(user.introduction),
    user.iconImageKey,
  )

case class UserRegisterInput(accountId: String,
                             email: String,
                             password: String,
                             displayName: String,
                             introduction: Option[String],
                             iconImageKey: Option[String])

object UserRegisterInput:
  implicit val reads: Reads[UserRegisterInput] = (
    (JsPath \ "account_id").read[String] ~
      (JsPath \ "email").read[String] ~
      (JsPath \ "password").read[String] ~
      (JsPath \ "display_name").read[String] ~
      (JsPath \ "introduction").readNullable[String] ~
      (JsPath \ "icon_image_key").readNullable[String]
    )(UserRegisterInput.apply _)

case class UserSignInInput(email: String, password: String)

object UserSignInInput:
  implicit val reads: Reads[UserSignInInput] = (
    (JsPath \ "email").read[String] ~
      (JsPath \ "password").read[String]
    )(UserSignInInput.apply _)
