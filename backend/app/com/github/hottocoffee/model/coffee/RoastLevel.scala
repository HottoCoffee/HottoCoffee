package com.github.hottocoffee.model.coffee

import scala.util.Left

enum RoastLevel(val value: String):
  case LIGHT extends RoastLevel("light")
  case CINNAMON extends RoastLevel("cinnamon")
  case MEDIUM extends RoastLevel("medium")
  case HIGH extends RoastLevel("high")
  case CITY extends RoastLevel("city")
  case FULL_CITY extends RoastLevel("full_city")
  case FRENCH extends RoastLevel("french")
  case ITALIAN extends RoastLevel("italian")

object RoastLevel:
  def apply(value: String): Either[Unit, RoastLevel] = {
    val trim = value.trim
    RoastLevel.values.find(_.value == trim) match
      case Some(value) => Right(value)
      case None => Left(())
  }
