package com.github.hottocoffee.model

case class Temperature private(value: Int)

object Temperature:
  def apply(value: Int): Either[Unit, Temperature] =
    if 0 <= value && value <= 100 then Right(new Temperature(value)) else Left(())   
