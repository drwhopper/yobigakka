package controllers

import akka.NotUsed
import akka.stream.scaladsl.Flow
import javax.inject._
import play.api._
import play.api.mvc._

/**
 * handle websocket
 */
@Singleton
class ChatController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  def chatRoom(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.chat())
  }

  def chatConnect(): WebSocket = {
    WebSocket.accept[String, String] {
      request => pass
    }
  }

  val pass: Flow[String, String, NotUsed] = Flow[String].map("I am a server. " + _)
}
