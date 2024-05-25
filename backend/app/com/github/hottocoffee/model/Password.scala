package com.github.hottocoffee.model

case class PlainPassword private(value: String)

object PlainPassword:
  def apply(value: String): Either[Unit, PlainPassword] =
    val trim = value.trim
    if trim.length >= 8 then Right(new PlainPassword(trim)) else Left(())

case class EncryptedPassword(value: String)
