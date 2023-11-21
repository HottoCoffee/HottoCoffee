package com.github.hottocoffee.controller.schema.response.error

enum ErrorResponse:
  case BAD_REQUEST(val status: Int = 400, val message: String)
