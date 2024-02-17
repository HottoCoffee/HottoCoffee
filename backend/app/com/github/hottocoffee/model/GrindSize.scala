package com.github.hottocoffee.model

enum GrindSize:
  case finest, fine, medium_fine, medium, coarse

object GrindSize:
  def apply(value: String): Either[Unit, GrindSize] = {
    val trim = value.trim
    GrindSize.values.find(_.toString == trim) match
      case Some(value) => Right(value)
      case None => Left(())
  }
