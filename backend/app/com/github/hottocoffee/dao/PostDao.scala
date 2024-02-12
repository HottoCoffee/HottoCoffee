package com.github.hottocoffee.dao

import anorm.*
import com.github.hottocoffee.model.{CoffeeOrigin, GramsOfCoffee, GramsOfWater, GrindSize, Location, RoastLevel, Temperature, WayToBrew}
import jakarta.inject.Inject
import play.api.db.Database

class PostDao @Inject()(db: Database) {
  def insert(userId: Int,
             location: Option[Location],
             origin: CoffeeOrigin,
             wayToBrew: Option[WayToBrew],
             roastLevel: Option[RoastLevel],
             temperature: Option[Temperature],
             gramsOfCoffee: Option[GramsOfCoffee],
             gramsOfWater: Option[GramsOfWater],
             grindSize: Option[GrindSize],
             impression: Option[String]): Option[Long] = {
    val id: Option[Long] = db.withConnection { implicit connection =>
      SQL(
        """
          insert into post (user_id, location, origin, way_to_brew, roast_level, temperature, grams_of_coffee, grams_of_water, grind_size, impression)
          values ({userId}, {location}, {origin}, {wayToBrew}, {roastLevel}, {temperature}, {gramsOfCoffee}, {gramsOfWater}, {grindSize}, {impression})
        """
      )
        .on(
          "userId" -> userId,
          "location" -> location.map(_.value),
          "origin" -> origin.value,
          "wayToBrew" -> wayToBrew.map(_.value),
          "roastLevel" -> roastLevel.map(_.toString),
          "temperature" -> temperature.map(_.value),
          "gramsOfCoffee" -> gramsOfCoffee.map(_.value),
          "gramsOfWater" -> gramsOfWater.map(_.value),
          "grindSize" -> grindSize.map(_.toString),
          "impression" -> impression,
        )
        .executeInsert()
    }
    id
  }
}

case class PostRecord(postId: Option[Int],
                      userId: Int,
                      location: Option[String],
                      origin: String,
                      wayToBrew: Option[String],
                      roastLevel: Option[String],
                      temperature: Option[Int],
                      gramsOfCoffee: Option[Int],
                      gramsOfWater: Option[Int],
                      grindSize: Option[String],
                      impression: Option[String])
