package com.github.hottocoffee.service

import org.mindrot.jbcrypt.BCrypt

object EncryptService:
  def encrypt(password: PlainPassword): EncryptedPassword =
    EncryptedPassword(BCrypt.hashpw(password.value, BCrypt.gensalt()))

  def isCorrectPassword(plainPassword: PlainPassword, encryptedPassword: EncryptedPassword): Boolean =
    BCrypt.checkpw(plainPassword.value, encryptedPassword.value)

case class PlainPassword(value: String)

case class EncryptedPassword(value: String)
