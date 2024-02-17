package com.github.hottocoffee.dao

class UserDao {
  def selectByUserIds(userIds: List[Int]): List[UserRecord] = List(UserRecord(2))
}

case class UserRecord(id: Int)
