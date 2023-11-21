package com.github.hottocoffee.controller.schema.response

import play.api.libs.json.{JsNull, Writes}

implicit def writesNullable[A](implicit writesA: Writes[A]): Writes[A | Null] = {
  case value: A => writesA.writes(value)
  case null => JsNull
}
