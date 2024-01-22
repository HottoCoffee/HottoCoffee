package com.github.hottocoffee.dao

import anorm.*
import jakarta.inject.Inject
import play.api.db.Database

class PostDao @Inject()(db: Database) {
  def insert(postRecord: PostRecord): PostRecord = {
    val id: Option[Long] = db.withConnection { implicit connection =>
      SQL(
        """
          insert into post (user_id, location, origin, way_to_brew, roast_level, temperature, grams_of_coffee, grams_of_water, grind_size, impression)
          values ({userId}, {location}, {origin}, {wayToBrew}, {roastLevel}, {temperature}, {gramsOfCoffee}, {gramsOfWater}, {grindSize}, {impression})
        """
      )
        .on(
          "userId" -> postRecord.userId,
          "location" -> postRecord.location,
          "origin" -> postRecord.origin,
          "wayToBrew" -> postRecord.wayToBrew,
          "roastLevel" -> postRecord.roastLevel,
          "temperature" -> postRecord.temperature,
          "gramsOfCoffee" -> postRecord.gramsOfCoffee,
          "gramsOfWater" -> postRecord.gramsOfWater,
          "grindSize" -> postRecord.grindSize,
          "impression" -> postRecord.impression,
        )
        .executeInsert()
    }
    postRecord.copy(postId = id.map(_.toInt))
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
