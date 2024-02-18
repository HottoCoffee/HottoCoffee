package com.github.hottocoffee.controller.schema.response

import play.api.libs.functional.syntax.toFunctionalBuilderOps
import play.api.libs.json.{JsPath, Writes}

case class TimelineOutput(posts: List[PostOutput],
                          hasNext: Boolean)

object TimelineOutput:
  implicit val timelineOutputWrites: Writes[TimelineOutput] = (
    (JsPath \ "posts").write[Seq[PostOutput]] ~
      (JsPath \ "has_next").write[Boolean]
    )(o => (o.posts, o.hasNext))
