package com.github.hottocoffee.controller.schema.response

import play.api.libs.json.{Json, Writes}

case class User(userId: Int,
                accountId: String,
                email: String,
                displayName: String,
                introduction: String,
                iconUrl: String)

object User:
  implicit val writes: Writes[User] = (o: User) => Json.obj(
    "user_id" -> o.userId,
    "account_id" -> o.accountId,
    "email" -> o.email,
    "display_name" -> o.displayName,
    "introduction" -> o.introduction,
    "icon_url" -> o.iconUrl
  )
