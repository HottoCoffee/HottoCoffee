package com.github.hottocoffee.dao

import anorm.*
import anorm.SqlParser.*
import com.github.hottocoffee.model.coffee.{CoffeeOrigin, GramsOfCoffee, GramsOfWater, GrindSize, Location, RoastLevel, Temperature, WayToBrew}
import jakarta.inject.Inject
import play.api.db.Database

class PostDao @Inject()(db: Database):
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
          "roastLevel" -> roastLevel.map(_.value),
          "temperature" -> temperature.map(_.value),
          "gramsOfCoffee" -> gramsOfCoffee.map(_.value),
          "gramsOfWater" -> gramsOfWater.map(_.value),
          "grindSize" -> grindSize.map(_.value),
          "impression" -> impression,
        )
        .executeInsert()
    }
    id
  }

  def selectLatest(count: Int): List[PostRecord] =
    db.withConnection { implicit connection =>
      SQL("select * from post order by id desc limit {count}")
        .on("count" -> count)
        .as(postRecordParser.*)
    }

  def selectLatestAfter(idExcluded: Int, count: Int): List[PostRecord] =
    db.withConnection { implicit connection =>
      SQL("select * from post where id > {id} order by id limit {count}")
        .on("id" -> idExcluded, "count" -> count)
        .as(postRecordParser.*)
    }.reverse

  def selectLatestBefore(idExcluded: Int, count: Int): List[PostRecord] =
    db.withConnection { implicit connection =>
      SQL("select * from post where id < {id} order by id desc limit {count}")
        .on("id" -> idExcluded, "count" -> count)
        .as(postRecordParser.*)
    }

  def selectExistEqualOrBefore(idIncluded: Int): Boolean =
    db.withConnection { implicit connection =>
      SQL("select exists(select 1 from post where id <= {id})")
        .on("id" -> idIncluded)
        .as(SqlParser.scalar[Int].single) == 1
    }

case class PostRecord(postId: Int,
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

private val postRecordParser = {
  int("post.id") ~
    int("post.user_id") ~
    get[Option[String]]("post.location") ~
    str("post.origin") ~
    get[Option[String]]("post.way_to_brew") ~
    get[Option[String]]("post.roast_level") ~
    get[Option[Int]]("post.temperature") ~
    get[Option[Int]]("post.grams_of_coffee") ~
    get[Option[Int]]("post.grams_of_water") ~
    get[Option[String]]("post.grind_size") ~
    get[Option[String]]("post.impression")
} map {
  case id ~ userId ~ location ~ origin ~ wayToBrew ~ roastLevel ~ temperature ~ gramsOfCoffee ~ gramsOfWater ~ grindSize ~ impression =>
    PostRecord(
      id,
      userId,
      location,
      origin,
      wayToBrew,
      roastLevel,
      temperature,
      gramsOfCoffee,
      gramsOfWater,
      grindSize,
      impression
    )
}

