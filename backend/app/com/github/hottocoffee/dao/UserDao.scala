package com.github.hottocoffee.dao

import anorm.{SQL, on}
import jakarta.inject.Inject
import play.db.Database

class UserDao @Inject()(db: Database) {
  def selectByUserIds(userIds: List[Int]): List[UserRecord] = List(UserRecord(2))

  def selectByEmailAndPassword(email: String, password: Array[Byte]): Option[UserRecord] = {
    db.withConnection { implicit connection =>
      SQL("select * from user where email = {email} and password = {password}")
        .on(
          "email" -> email,
          "password" -> password,
        )
    }
  }
}

case class UserRecord(id: Int)
