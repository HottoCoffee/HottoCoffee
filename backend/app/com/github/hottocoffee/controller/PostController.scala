package com.github.hottocoffee.controller

import com.github.hottocoffee.controller.schema.response.{PostInput, PostOutput, RoastLevel, UserInfoOutput}
import com.github.hottocoffee.dao.{PostDao, PostRecord}
import com.github.hottocoffee.util.value2Optional
import jakarta.inject.{Inject, Singleton}
import play.api.libs.json.{JsError, JsSuccess, Json}
import play.api.mvc.{Action, BaseController, ControllerComponents}

import scala.util.chaining.*

@Singleton
class PostController @Inject()(val controllerComponents: ControllerComponents, val postDao: PostDao) extends BaseController {
  def find(postId: Int): Action[_] = Action {
    postId match
      case 1 => PostOutput(
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
        temperature = None,
        gramsOfCoffee = None,
        gramsOfWater = None,
        grindSize = None,
        impression = "Wow",
      ).pipe(Json.toJson)
        .pipe(Ok(_))

      case 2 => PostOutput(
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
        roastLevel = None,
        temperature = None,
        gramsOfCoffee = None,
        gramsOfWater = None,
        grindSize = None,
        impression = None,
      ).pipe(Json.toJson)
        .pipe(Ok(_))

      case _ => NotFound
  }

  def post(): Action[_] = Action { request =>
    request.body.asJson match
      case None => UnsupportedMediaType
      case Some(json) => json.validate[PostInput] match
        case e: JsError => BadRequest
        case JsSuccess(body, _) =>
          postDao.insert(
            PostRecord(
              postId = None,
              userId = 1,
              location = "home",
              origin = "Kenya",
              wayToBrew = "Latte",
              roastLevel = "medium",
              temperature = None,
              gramsOfCoffee = None,
              gramsOfWater = None,
              grindSize = None,
              impression = "Wow"
            )
          )
          val output = PostOutput(
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
            temperature = None,
            gramsOfCoffee = None,
            gramsOfWater = None,
            grindSize = None,
            impression = "Wow",
          )
          println(output)
          output.pipe(Json.toJson)
            .pipe(Created(_))
  }

  def delete(postId: Int): Action[_] = Action {
    Ok
  }
}
