package com.github.hottocoffee.controller

import com.github.hottocoffee.controller.auth.LoginRequiredAction
import jakarta.inject.Singleton
import play.api.mvc.{Action, BaseController, ControllerComponents}

import javax.inject.Inject

@Singleton
class LoginController @Inject()
(val controllerComponents: ControllerComponents,
 val loginRequiredAction: LoginRequiredAction)
  extends BaseController:

  def signUp(): Action[_] = Action {
    Ok
  }

  def signIn(): Action[_] = Action {
    Ok
  }

  def signOut(): Action[_] = loginRequiredAction {
    Ok
  }
