package com.github.hottocoffee.model

/**
 * How much weight (gram) of coffee beans are used to brew the coffee
 */
case class GramsOfCoffee private(value: Int)

object GramsOfCoffee:
  def apply(value: Int): Either[Unit, GramsOfCoffee] =
    if value >= 1 then Right(new GramsOfCoffee(value)) else Left(())
