package com.github.hottocoffee.controller

import com.github.hottocoffee.controller.schema.response.{PostOutput, TimelineOutput, UserInfoOutput}
import com.github.hottocoffee.dao.PostRecord
import com.github.hottocoffee.model.User
import com.github.hottocoffee.model.coffee.{CoffeeOrigin, GramsOfCoffee, GramsOfWater, GrindSize, Location, RoastLevel, Temperature, WayToBrew}
import com.github.hottocoffee.service.TimelineService
import com.github.hottocoffee.util.nullable2Optional
import jakarta.inject.{Inject, Singleton}
import play.api.Logger
import play.api.libs.json.Json
import play.api.mvc.Results.{InternalServerError, Ok}
import play.api.mvc.{Action, BaseController, ControllerComponents}

import scala.util.chaining.*

@Singleton
class TimelineController @Inject()(val controllerComponents: ControllerComponents, val timelineService: TimelineService) extends BaseController:
  private val logger = Logger(this.getClass)

  def list(lower_post_id: Option[Int], upper_post_id: Option[Int]): Action[_] = Action {
    (lower_post_id, upper_post_id) match
      case (Some(_), Some(_)) => BadRequest
      case _ =>
        val timeline = (lower_post_id, upper_post_id) match
          case (None, None) => timelineService.getLatestPosts
          case (Some(lowerPostId), _) => timelineService.getLatestPostsNewerThan(lowerPostId)
          case (_, Some(upperPostId)) => timelineService.getLatestPostsOlderThan(upperPostId)

        timeline match
          case Left(message) =>
            logger.warn(message)
            InternalServerError
          case Right((list, hasNext)) => list.map(convert).partitionMap(identity) match
            case (Nil, posts) => TimelineOutput(posts, hasNext).pipe(Json.toJson).pipe(Ok(_))
            case _ => InternalServerError
  }

  private def convert(postRecord: PostRecord, user: User): Either[Unit, PostOutput] =
    for {
      location <- postRecord.location.toOptionEither(Location(_))
      origin <- CoffeeOrigin(postRecord.origin)
      wayToBrew <- postRecord.wayToBrew.toOptionEither(WayToBrew(_))
      roastLevel <- postRecord.roastLevel.toOptionEither(RoastLevel(_))
      temperature <- postRecord.temperature.toOptionEither(Temperature(_))
      gramsOfCoffee <- postRecord.gramsOfCoffee.toOptionEither(GramsOfCoffee(_))
      gramsOfWater <- postRecord.gramsOfWater.toOptionEither(GramsOfWater(_))
      grindSize <- postRecord.grindSize.toOptionEither(GrindSize(_))
    } yield PostOutput(
      postRecord.postId,
      UserInfoOutput(
        userId = user.id.toInt,
        accountId = user.accountId,
        displayName = user.displayName,
        iconUrl = user.iconUrl,
      ),
      location,
      origin,
      wayToBrew,
      roastLevel,
      temperature,
      gramsOfCoffee,
      gramsOfWater,
      grindSize,
      postRecord.impression,
    )

extension [A](option: Option[A])
  def toOptionEither[B](f: A => Either[Unit, B]): Either[Unit, Option[B]] = option match
    case Some(value) => f(value).map(Some(_))
    case None => Right(None)
