package com.github.hottocoffee.auth

import jakarta.inject.Inject
import play.api.mvc.Results.Unauthorized
import play.api.mvc.{ActionBuilderImpl, BodyParsers, Request, Result}

import scala.concurrent.{ExecutionContext, Future}

val USER_ID = "user_id"

class LoginRequiredAction @Inject()(parser: BodyParsers.Default)(implicit ec: ExecutionContext) extends ActionBuilderImpl(parser) {
  override def invokeBlock[A](request: Request[A], block: Request[A] => Future[Result]): Future[Result] =
    request.session.get(USER_ID) match
      case Some(userId) => block(request)
      case None => Future(Unauthorized)
}
