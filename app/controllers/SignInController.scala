package controllers

import com.mohiva.play.silhouette.api._
import com.mohiva.play.silhouette.api.util.Credentials
import com.mohiva.play.silhouette.impl.providers.CredentialsProvider
import javax.inject.{Inject, Singleton}
import models.{DefaultEnv, SignInForm}
import models.services.{SignUpService, UserAlreadyExists, UserCreated}
import play.api.Logger
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents, Request}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class SignInController @Inject()(val controllerComponents: ControllerComponents,
                                 credentialsProvider: CredentialsProvider,
                                 silhouette: Silhouette[DefaultEnv],
                                 signUpService: SignUpService)(implicit ex: ExecutionContext) extends BaseController {

  val logger: Logger = Logger(this.getClass)

  def submit: Action[AnyContent] = silhouette.UnsecuredAction.async  {
    implicit request: Request[AnyContent] => {
      SignInForm.form.bindFromRequest.fold(
        _ => Future.successful(BadRequest),
        data => {
          val credentials = Credentials(data.userID, data.password)
          credentialsProvider.authenticate(credentials)
          Future.successful(Ok)
        }
      )
    }
  }

}
