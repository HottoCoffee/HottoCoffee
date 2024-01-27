package com.github.hottocoffee.model

/**
 * How the coffee is brewed (e.g., Latte)
 */
case class WayToBrew private(value: String)

object WayToBrew:
  def apply(value: String): Either[Unit, WayToBrew] = {
    val trim = value.trim
    if trim.nonEmpty && trim.length <= 100 then Right(new WayToBrew(value)) else Left(())
  }
