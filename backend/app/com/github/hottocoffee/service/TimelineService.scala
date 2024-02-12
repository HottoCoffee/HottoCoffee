package com.github.hottocoffee.service

import com.github.hottocoffee.dao.{PostDao, PostRecord, UserDao, UserRecord}
import jakarta.inject.{Inject, Singleton}


@Singleton
class TimelineService @Inject()(val postDao: PostDao, val userDao: UserDao):
  private val POST_COUNT_IN_TIMELINE = 50

  def getLatestPosts: Either[Unit, List[(PostRecord, UserRecord)]] =
    combineWithUser(postDao.selectLatest(POST_COUNT_IN_TIMELINE))

  def getLatestPostsNewerThan(postId: Int): Either[Unit, List[(PostRecord, UserRecord)]] =
    combineWithUser(postDao.selectLatestAfter(postId, POST_COUNT_IN_TIMELINE))

  def getLatestPostsOlderThan(postId: Int): Either[Unit, List[(PostRecord, UserRecord)]] =
    combineWithUser(postDao.selectLatestBefore(postId, POST_COUNT_IN_TIMELINE))

  private def combineWithUser(postRecords: List[PostRecord]): Either[Unit, List[(PostRecord, UserRecord)]] = {
    val userIds = postRecords.map(_.userId).distinct
    val userById = userDao.selectByUserIds(userIds)
      .map(userRecord => (userRecord.id, userRecord))
      .toMap

    postRecords.map(postRecord => userById.get(postRecord.userId) match
      case None => Left(())
      case Some(userRecord) => Right((postRecord, userRecord))
    ).partitionMap(identity) match
      case (Nil, rights) => Right(rights)
      case _ => Left(())
  }
