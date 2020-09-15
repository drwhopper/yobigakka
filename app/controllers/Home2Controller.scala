package controllers

import com.mohiva.play.silhouette.api.Silhouette
import javax.inject._
import models.DefaultEnv
import models.services.SignUpService
import play.api.mvc._
import play.filters.csrf.CSRF
import play.filters.csrf.CSRF.Token

import scala.concurrent.{ExecutionContext, Future}

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class Home2Controller @Inject()(val controllerComponents: ControllerComponents,
                                                       silhouette: Silhouette[DefaultEnv],
                                                       signUpService: SignUpService)(implicit ex: ExecutionContext) extends BaseController {

  /**
   * Just load vue app.
   */
  def index(): Action[AnyContent] = silhouette.SecuredAction.async { implicit request: Request[AnyContent] =>
    val Token(_, value) = CSRF.getToken.get
    Future.successful(Ok(views.html.index(value)))
  }

}
