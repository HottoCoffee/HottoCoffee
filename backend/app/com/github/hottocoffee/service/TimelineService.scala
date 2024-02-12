package com.github.hottocoffee.service

import com.github.hottocoffee.dao.{PostDao, PostRecord, UserDao, UserRecord}
import jakarta.inject.{Inject, Singleton}


@Singleton
class TimelineService @Inject()(val postDao: PostDao, val userDao: UserDao) {
  private val POST_COUNT_IN_TIMELINE = 50

  def getLatestPosts: Option[List[(PostRecord, UserRecord)]] = {
    val postRecords = postDao.selectLatest(POST_COUNT_IN_TIMELINE)
    val userIds = postRecords.map(_.userId).distinct
    val userById = userDao.selectByUserIds(userIds)
      .map(userRecord => (userRecord.id, userRecord))
      .toMap

    postRecords.map(postRecord => userById.get(postRecord.userId).map((postRecord, _)))
      .foldLeft(Option(List[(PostRecord, UserRecord)]()))((acc, record) =>
        (acc, record) match
          case (Some(acc), Some(record)) => Some(acc :+ record)
          case _ => None
      )
  }

  // def getLatestPostsBefore
  // def getLatestPostsAfter
}
