package controllers

import javax.inject._
import play.api._
import play.api.mvc._


class SignUpController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  def submit: Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Redirect(routes.ChatController.chatRoom())
  }
}
