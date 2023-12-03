package com.github.hottocoffee.controller

import com.github.hottocoffee.controller.schema.response.{Post, RoastLevel, UserInfo}
import jakarta.inject.{Inject, Singleton}
import play.api.libs.json.Json
import play.api.mvc.{Action, BaseController, ControllerComponents}

import scala.util.chaining.*

@Singleton
class PostController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {
  def find(postId: Int): Action[_] = Action {
    postId match
      case 1 => Post(
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
      ).pipe(Json.toJson)
        .pipe(Ok(_))

      case 2 => Post(
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
      ).pipe(Json.toJson)
        .pipe(Ok(_))

      case _ => NotFound
  }
}
