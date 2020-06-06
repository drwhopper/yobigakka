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
    Ok(views.html.chat()).withSession("id" -> "ya")
  }

  def chatConnect(): WebSocket = {
    WebSocket.accept[String, String] {
      request => newflow(request)
    }
  }

  def newflow(request: RequestHeader): Flow[String, String, NotUsed] = {
    Flow[String].map(request.session.get("id").getOrElse("no") + _)
  }
}
