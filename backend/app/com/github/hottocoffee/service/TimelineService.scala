package com.github.hottocoffee.service

import com.github.hottocoffee.dao.{PostDao, PostRecord, UserDao}
import com.github.hottocoffee.model.User
import jakarta.inject.{Inject, Singleton}

@Singleton
class TimelineService @Inject()(val postDao: PostDao, val userDao: UserDao):
  private val POST_COUNT_IN_TIMELINE = 50

  def getLatestPosts: Either[String, (List[(PostRecord, User)], Boolean)] =
    combineWithUser(postDao.selectLatest(POST_COUNT_IN_TIMELINE + 1))
      .map(list => (list.slice(0, POST_COUNT_IN_TIMELINE), list.size == POST_COUNT_IN_TIMELINE + 1))

  def getLatestPostsNewerThan(postId: Int): Either[String, (List[(PostRecord, User)], Boolean)] =
    combineWithUser(postDao.selectLatestAfter(postId, POST_COUNT_IN_TIMELINE))
      .map(list => (list, postDao.selectExistEqualOrBefore(postId)))

  def getLatestPostsOlderThan(postId: Int): Either[String, (List[(PostRecord, User)], Boolean)] =
    combineWithUser(postDao.selectLatestBefore(postId, POST_COUNT_IN_TIMELINE + 1))
      .map(list => (list.slice(0, POST_COUNT_IN_TIMELINE), list.size == POST_COUNT_IN_TIMELINE + 1))

  private def combineWithUser(postRecords: List[PostRecord]): Either[String, List[(PostRecord, User)]] = {
    val userIds = postRecords.map(_.userId).distinct
    val userById = userDao.selectByUserIds(userIds)
      .map(user => (user.id, user))
      .toMap

    postRecords.map(postRecord => userById.get(postRecord.userId) match
      case None => Left(postRecord.userId)
      case Some(user) => Right((postRecord, user))
    ).partitionMap(identity) match
      case (Nil, rights) => Right(rights)
      case (lefts, _) => Left(s"User not found: ids $lefts")
  }
