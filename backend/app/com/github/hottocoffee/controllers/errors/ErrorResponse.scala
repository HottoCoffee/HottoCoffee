package com.github.hottocoffee.controllers.errors

enum ErrorResponse:
  case BAD_REQUEST(val status: Int = 400, val message: String)
