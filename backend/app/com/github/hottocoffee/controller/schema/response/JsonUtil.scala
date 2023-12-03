package com.github.hottocoffee.controller.schema.response

import play.api.libs.json.{JsNull, Reads, Writes}

implicit def writesNullable[A](implicit writesA: Writes[A]): Writes[A | Null] = {
  case value: A => writesA.writes(value)
  case null => JsNull
}

implicit def readsNullable[A](implicit readsA: Reads[A]): Reads[A | Null] = {
  case JsNull => null
  case other => readsA.reads(other)
}
