package com.github.hottocoffee.model.coffee

enum GrindSize(val value: String):
  case FINEST extends GrindSize("finest")
  case FINE extends GrindSize("fine")
  case MEDIUM_FINE extends GrindSize("medium_fine")
  case MEDIUM extends GrindSize("medium")
  case COARSE extends GrindSize("coarse")

object GrindSize:
  def apply(value: String): Either[Unit, GrindSize] = {
    val trim = value.trim
    GrindSize.values.find(_.value == trim) match
      case Some(it) => Right(it)
      case None => Left(())
  }
