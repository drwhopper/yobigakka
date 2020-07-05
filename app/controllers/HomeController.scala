package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.filters.csrf.CSRF
import play.filters.csrf.CSRF.Token

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  /**
   * Just load vue app.
   */
  def index(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    val Token(_, value) = CSRF.getToken.get
    Ok(views.html.index(value))
  }

}
