package com.github.hottocoffee.model

import scala.util.Left

enum RoastLevel:
  case light, cinnamon, medium, high, city, full_city, french, italian

object RoastLevel:
  def apply(value: String): Either[Unit, RoastLevel] = {
    val trim = value.trim
    RoastLevel.values.find(_.toString == trim) match
      case Some(value) => Right(value)
      case None => Left(())
  }
