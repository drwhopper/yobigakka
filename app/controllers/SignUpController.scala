package controllers

import com.mohiva.play.silhouette.api._
import javax.inject._
import models.{DefaultEnv, SignUpForm}
import play.api._
import play.api.Logger
import play.api.mvc._
import models.services._

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class SignUpController @Inject()(val controllerComponents: ControllerComponents,
                                 silhouette: Silhouette[DefaultEnv],
                                 signUpService: SignUpService)(implicit ex: ExecutionContext) extends BaseController {

  val logger: Logger = Logger(this.getClass)

  def submit: Action[AnyContent] = silhouette.UnsecuredAction.async  {
    implicit request: Request[AnyContent] => {
      SignUpForm.form.bindFromRequest.fold(
        _ => Future.successful(BadRequest),
        data => {
          signUpService.signUpByCredentials(data).map {
            case UserCreated(user) =>
              silhouette.env.eventBus.publish(SignUpEvent(user, request))
              Ok
            case UserAlreadyExists =>
              Conflict
          }
        }
      )
    }
  }

}
