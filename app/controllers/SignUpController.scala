package controllers

import com.mohiva.play.silhouette.api.{Environment, _}
import javax.inject._
import models.{DefaultEnv, SignUpForm}
import play.api._
import play.api.Logger
import play.api.mvc._
import models.services.SignUpService

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class SignUpController @Inject()(val controllerComponents: ControllerComponents,
                                 silhouette: Silhouette[DefaultEnv],
                                 signUpService: SignUpService) extends BaseController {

  val logger: Logger = Logger(this.getClass)

  def submit: Action[AnyContent] = silhouette.UnsecuredAction.async  {
    implicit request: Request[AnyContent] => {
      SignUpForm.form.bindFromRequest.fold(
        a => {
          logger.warn(a.toString)
          Future.successful(BadRequest)
        },
        _ => Future.successful(Ok)
      )
    }
  }

}
