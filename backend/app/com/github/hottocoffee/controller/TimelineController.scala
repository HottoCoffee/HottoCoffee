package com.github.hottocoffee.controller

import com.github.hottocoffee.controller.schema.response.{PostOutput, UserInfoOutput}
import com.github.hottocoffee.dao.{PostRecord, UserRecord}
import com.github.hottocoffee.model.{CoffeeOrigin, GramsOfCoffee, GramsOfWater, GrindSize, Location, RoastLevel, Temperature, WayToBrew}
import com.github.hottocoffee.service.TimelineService
import jakarta.inject.{Inject, Singleton}
import play.api.libs.json.{Json, Writes}
import play.api.mvc.{Action, BaseController, ControllerComponents}

import scala.util.chaining.*

@Singleton
class TimelineController @Inject()(val controllerComponents: ControllerComponents, val timelineService: TimelineService) extends BaseController:
  def list(lower_post_id: Option[Int], upper_post_id: Option[Int]): Action[_] = Action {
    (lower_post_id, upper_post_id) match
      case (Some(_), Some(_)) => BadRequest
      case (None, None) => timelineService.getLatestPosts match
        case Left(_) => InternalServerError
        case Right(list) => list.map(convert).partitionMap(identity) match
          case (Nil, rights) => rights.pipe(Json.toJson).pipe(Ok(_))
          case _ => InternalServerError
      case (Some(lowerPostId), _) => timelineService.getLatestPostsNewerThan(lowerPostId) match
        case Left(_) => InternalServerError
        case Right(list) => list.map(convert).partitionMap(identity) match
          case (Nil, rights) => rights.pipe(Json.toJson).pipe(Ok(_))
          case _ => InternalServerError
      case (_, Some(upperPostId)) => timelineService.getLatestPostsOlderThan(upperPostId) match
        case Left(_) => InternalServerError
        case Right(list) => list.map(convert).partitionMap(identity) match
          case (Nil, rights) => rights.pipe(Json.toJson).pipe(Ok(_))
          case _ => InternalServerError
  }

  private def convert(postRecord: PostRecord, userRecord: UserRecord): Either[Unit, PostOutput] =
    for {
      location <- postRecord.location.toOptionEither(Location(_))
      origin <- CoffeeOrigin(postRecord.origin)
      wayToBrew <- postRecord.wayToBrew.toOptionEither(WayToBrew(_))
      roastLevel <- postRecord.wayToBrew.toOptionEither(RoastLevel(_))
      temperature <- postRecord.temperature.toOptionEither(Temperature(_))
      gramsOfCoffee <- postRecord.gramsOfCoffee.toOptionEither(GramsOfCoffee(_))
      gramsOfWater <- postRecord.gramsOfWater.toOptionEither(GramsOfWater(_))
      grindSize <- postRecord.grindSize.toOptionEither(GrindSize(_))
    } yield PostOutput(
      postRecord.postId,
      UserInfoOutput(
        userId = userRecord.id,
        accountId = "seito2", // TODO
        displayName = "seito_hirai",
        iconUrl = Some("https://avatars.githubusercontent.com/u/42537610?v=4"),
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
