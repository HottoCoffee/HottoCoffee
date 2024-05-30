package com.github.hottocoffee.controller.auth

import jakarta.inject.Inject
import pdi.jwt.{Jwt, JwtAlgorithm}
import play.api.libs.json.Json
import play.api.mvc.Results.Unauthorized
import play.api.mvc.Security.AuthenticatedBuilder
import play.api.mvc.{AnyContent, BodyParser, BodyParsers, Results}

import scala.annotation.unused
import scala.concurrent.ExecutionContext

val jwtKey = "secret"

class LoginRequiredAction(parser: BodyParser[AnyContent])(implicit ec: ExecutionContext)
  extends AuthenticatedBuilder[Int]({ header =>
    header.headers.get("Authorization")
      .map(_.replace("Bearer ", ""))
      .flatMap(token => Jwt.decode(token, jwtKey, Seq(JwtAlgorithm.HS256)).toOption)
      .flatMap(claim => (Json.parse(claim.content) \ "id").toOption)
      .flatMap(jsValue => jsValue.toString.toIntOption)
  }, parser, * => Unauthorized):

  @Inject()
  def this(parser: BodyParsers.Default)(implicit @unused ec: ExecutionContext) = {
    this(parser: BodyParser[AnyContent])
  }
