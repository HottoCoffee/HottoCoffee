package com.github.hottocoffee.service

import com.github.hottocoffee.dao.UserDao
import com.github.hottocoffee.model.User
import jakarta.inject.Inject

import scala.util.{Failure, Success, Try}

class RegisterUserService @Inject()(private val userDao: UserDao):
  def register(accountId: String,
               email: String,
               password: PlainPassword,
               displayName: String,
               introduction: String,
               iconUrl: String | Null): Either[String, User] =
    val isDuplicated = userDao.selectCountByAccountIdOrEmail(accountId, email) == 0
    if isDuplicated then Left("Duplicated accountId or email")
    else Try(
      userDao.insert(
        accountId,
        email,
        password,
        displayName,
        introduction,
        iconUrl
      )
    ) match
      case Failure(exception) => Left(exception.getMessage + "\n" + exception.getStackTrace.mkString("\n"))
      case Success(value) => value match
        case Some(value) => Right(value)
        case None => Left("Failed by unknown reason")
