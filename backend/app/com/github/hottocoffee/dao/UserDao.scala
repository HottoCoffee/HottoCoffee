package com.github.hottocoffee.dao

import anorm.SqlParser.{get, long, scalar, str}
import anorm.{SQL, on, ~}
import com.github.hottocoffee.model.{EncryptedPassword, PlainPassword, User}
import com.github.hottocoffee.service.EncryptService
import com.github.hottocoffee.util.{nullable2Optional, option2Nullable}
import jakarta.inject.Inject
import play.api.db.Database

class UserDao @Inject()(db: Database) {
  def selectByUserId(userId: Int): Option[User] =
    db.withConnection { implicit connection =>
        SQL("select * from user where id = {userId}")
          .on("id" -> userId)
          .as(userRecordParser.singleOpt)
      }
      .map(_.toUser)

  def selectByUserIds(userIds: List[Int]): List[User] = List.empty

  def selectByAccountId(accountId: String): Option[User] =
    db.withConnection { implicit connection =>
        SQL("select * from user where account_id = {accountId}")
          .on("accountId" -> accountId)
          .as(userRecordParser.singleOpt)
      }
      .map(_.toUser)

  def selectByEmailAndPassword(email: String, password: PlainPassword): Option[User] =
    db.withConnection { implicit connection =>
        SQL("select * from user where email = {email}")
          .on("email" -> email)
          .as(userRecordParser.singleOpt)
      }
      .filter(record => EncryptService.isCorrectPassword(password, EncryptedPassword(record.password)))
      .map(_.toUser)

  def selectCountByAccountIdOrEmail(accountId: String, email: String): Long =
    db.withConnection { implicit connection =>
      SQL("select count(*) from user where account_id = {accountId} or email = {email}")
        .on("accountId" -> accountId)
        .on("email" -> email)
        .as(scalar[Long].single)
    }

  def insert(accountId: String,
             email: String,
             password: PlainPassword,
             displayName: String,
             introduction: String,
             iconUrl: String | Null,
            ): Option[User] =
    val optionId: Option[Long] = db.withConnection { implicit connection =>
      SQL(
        """
          insert into user (account_id, email, password, display_name, introduction, icon_url)
          values ({accountId}, {email}, {password}, {displayName}, {introduction}, {iconUrl})
        """
      )
        .on(
          "accountId" -> accountId,
          "email" -> email,
          "password" -> EncryptService.encrypt(password).value,
          "displayName" -> displayName,
          "introduction" -> introduction,
          "iconUrl" -> nullable2Optional(iconUrl)
        )
        .executeInsert()
    }
    optionId.map(id => User(id, accountId, email, displayName, introduction, iconUrl))
}

private case class UserRecord(id: Long,
                              accountId: String,
                              email: String,
                              password: String,
                              displayName: String,
                              introduction: String,
                              iconUrl: Option[String]):
  def toUser: User = User(id, accountId, email, displayName, introduction, iconUrl)

private val userRecordParser = {
  long("user.id") ~
    str("user.account_id") ~
    str("user.email") ~
    str("user.password") ~
    str("user.display_name") ~
    str("user.introduction") ~
    get[Option[String]]("user.icon_url")
} map {
  case id ~ accountId ~ email ~ password ~ displayName ~ introduction ~ iconUrl =>
    UserRecord(
      id,
      accountId,
      email,
      password,
      displayName,
      introduction,
      iconUrl,
    )
}
