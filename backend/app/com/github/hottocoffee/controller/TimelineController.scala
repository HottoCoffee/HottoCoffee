package com.github.hottocoffee.controller

import com.github.hottocoffee.controller.schema.response.{PostOutput, RoastLevel, UserInfoOutput}
import com.github.hottocoffee.util.value2Optional
import jakarta.inject.{Inject, Singleton}
import play.api.libs.json.{Json, Writes}
import play.api.mvc.{Action, BaseController, ControllerComponents}

import scala.util.chaining.*


@Singleton
class TimelineController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {
  def list(lower_post_id: Option[Int], upper_post_id: Option[Int]): Action[_] = Action {
    Seq(
      PostOutput(
        postId = 1,
        userInfo = UserInfoOutput(
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
      PostOutput(
        postId = 2,
        userInfo = UserInfoOutput(
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
