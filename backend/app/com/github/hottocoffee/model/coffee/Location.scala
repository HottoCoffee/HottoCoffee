package com.github.hottocoffee.model.coffee

/**
 * Where the coffee is drunk
 */
case class Location private(value: String)

object Location:
  def apply(value: String): Either[Unit, Location] = {
    val trim = value.trim
    if trim.nonEmpty && trim.length <= 100 then Right(new Location(trim)) else Left(())
  }
