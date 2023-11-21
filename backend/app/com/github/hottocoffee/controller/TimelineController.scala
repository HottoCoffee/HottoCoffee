package com.github.hottocoffee.controller

import com.github.hottocoffee.controller.schema.response.{Post, RoastLevel, UserInfo}
import jakarta.inject.{Inject, Singleton}
import play.api.libs.json.{JsNull, JsString, Json, Writes}
import play.api.mvc.{Action, BaseController, ControllerComponents}
import play.components.BaseComponents

import scala.util.chaining.*

@Singleton
class TimelineController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {
  def list(lower_post_id: Option[Int], upper_post_id: Option[Int]): Action[_] = Action {
    Seq(
      Post(
        postId = 1,
        userInfo = UserInfo(
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
      ),
      Post(
        postId = 2,
        userInfo = UserInfo(
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
      )
    ).pipe(Json.toJson)
      .pipe(Ok(_))
  }
}
