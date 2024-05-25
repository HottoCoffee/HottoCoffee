package com.github.hottocoffee.model.coffee

/**
 * Countries where the coffee is harvested (e.g., BrazilS)
 */
case class CoffeeOrigin private(value: String)

object CoffeeOrigin:
  def apply(value: String): Either[Unit, CoffeeOrigin] = {
    val trim = value.trim
    if trim.nonEmpty && trim.length <= 100 then Right(new CoffeeOrigin(trim)) else Left(())
  }
