package com.github.hottocoffee.util

import scala.language.implicitConversions

implicit def value2Optional[A](value: A): Option[A] = value match
  case null => None
  case _ => Some(value)

implicit def null2Optional(value: Null): Option[_] = None

implicit def nullable2Optional[A](value: A | Null): Option[A] = value match
  case a: A => Some(a)
  case null => None

implicit def option2Nullable[A](value: Option[A]): A | Null = value match
  case Some(a) => a
  case None => null
