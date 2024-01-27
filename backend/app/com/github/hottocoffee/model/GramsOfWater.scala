package com.github.hottocoffee.model

/**
 * How much weight (gram) of water are used to brew the coffee
 */
case class GramsOfWater private(value: Int)

object GramsOfWater:
  def apply(value: Int): Either[Unit, GramsOfWater] =
    if value >= 1 then Right(new GramsOfWater(value)) else Left(())
