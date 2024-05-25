package com.github.hottocoffee.service

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers.be
import org.scalatest.matchers.should.Matchers.should

class EncryptServiceTest extends AnyFlatSpec:
  "EncryptService" should "encrypt plain password" in {
    val plainPassword = PlainPassword("foo")
    val encryptedPassword = EncryptService.encrypt(plainPassword)
    EncryptService.isCorrectPassword(plainPassword, encryptedPassword) should be(true)
    EncryptService.isCorrectPassword(PlainPassword("bar"), encryptedPassword) should be(false)
  }
