package com.github.hottocoffee.controller

import com.github.hottocoffee.controller.auth.LoginRequiredAction
import com.github.hottocoffee.controller.schema.response.{PostInput, PostOutput, UserInfoOutput}
import com.github.hottocoffee.dao.PostDao
import com.github.hottocoffee.model.coffee.{CoffeeOrigin, Location, RoastLevel, WayToBrew}
import com.github.hottocoffee.util.value2Optional
import jakarta.inject.{Inject, Singleton}
import play.api.libs.json.{JsError, JsSuccess, Json}
import play.api.mvc.{Action, BaseController, ControllerComponents}

import scala.util.chaining.*

@Singleton
class PostController @Inject()
(val controllerComponents: ControllerComponents, val postDao: PostDao, val loginRequiredAction: LoginRequiredAction)
  extends BaseController:
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
        location = Location("home").toOption,
        origin = CoffeeOrigin("Kenya").toOption.get,
        wayToBrew = WayToBrew("Latte").toOption,
        roastLevel = RoastLevel.medium,
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
        location = Location("home").toOption,
        origin = CoffeeOrigin("Kenya").toOption.get,
        wayToBrew = WayToBrew("Latte").toOption,
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

  def post(): Action[_] = loginRequiredAction { request =>
    request.body.asJson match
      case None => UnsupportedMediaType
      case Some(json) =>
        json.validate[PostInput] match
          case _: JsError => BadRequest
          case JsSuccess(body, _) =>
            postDao.insert(
              userId = request.user,
              location = body.location,
              origin = body.origin,
              wayToBrew = body.wayToBrew,
              roastLevel = body.roastLevel,
              temperature = body.temperature,
              gramsOfCoffee = body.gramsOfCoffee,
              gramsOfWater = body.gramsOfWater,
              grindSize = body.grindSize,
              impression = body.impression,
            ) match
              case Some(id) => PostOutput(
                postId = id.toInt,
                userInfo = UserInfoOutput(
                  userId = 2,
                  accountId = "seito2",
                  displayName = "seito_hirai",
                  iconUrl = "https://avatars.githubusercontent.com/u/42537610?v=4",
                ),
                location = body.location,
                origin = body.origin,
                wayToBrew = body.wayToBrew,
                roastLevel = body.roastLevel,
                temperature = body.temperature,
                gramsOfCoffee = body.gramsOfCoffee,
                gramsOfWater = body.gramsOfWater,
                grindSize = body.grindSize,
                impression = body.impression,
              ).pipe(Json.toJson)
                .pipe(Created(_))
              case _ => InternalServerError
  }

  def delete(postId: Int): Action[_] = Action {
    Ok
  }
