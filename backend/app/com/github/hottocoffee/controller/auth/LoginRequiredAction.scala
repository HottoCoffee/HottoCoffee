package com.github.hottocoffee.controller.auth

import jakarta.inject.Inject
import play.api.mvc.Results.Unauthorized
import play.api.mvc.Security.AuthenticatedBuilder
import play.api.mvc.{AnyContent, BodyParser, BodyParsers}

import scala.concurrent.ExecutionContext

val USER_ID = "user_id"

class LoginRequiredAction(parser: BodyParser[AnyContent])(implicit ec: ExecutionContext)
  extends AuthenticatedBuilder[Int]({
    _.session.get(USER_ID).flatMap(_.toIntOption)
  }, parser, * => Unauthorized):
  @Inject()
  def this(parser: BodyParsers.Default)(implicit ec: ExecutionContext) = {
    this(parser: BodyParser[AnyContent])
  }
