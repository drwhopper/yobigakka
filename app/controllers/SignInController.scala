package controllers

import com.mohiva.play.silhouette.api._
import com.mohiva.play.silhouette.api.exceptions.ProviderException
import com.mohiva.play.silhouette.api.util.Credentials
import com.mohiva.play.silhouette.impl.exceptions.{IdentityNotFoundException, InvalidPasswordException}
import com.mohiva.play.silhouette.impl.providers.CredentialsProvider
import javax.inject.{Inject, Singleton}
import models.services.UserService
import models.{DefaultEnv, SignInForm}
import play.api.Logger
import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents, Request}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class SignInController @Inject()(val controllerComponents: ControllerComponents,
                                 userService: UserService,
                                 credentialsProvider: CredentialsProvider,
                                 silhouette: Silhouette[DefaultEnv])(implicit ex: ExecutionContext) extends BaseController {

  val logger: Logger = Logger(this.getClass)

  def submit: Action[AnyContent] = silhouette.UnsecuredAction.async {
    implicit request: Request[AnyContent] => {
      SignInForm.form.bindFromRequest.fold(
        _ => Future.successful(BadRequest),
        data => {
          val credentials = Credentials(data.userID, data.password)
          credentialsProvider.authenticate(credentials).flatMap { loginInfo =>
            userService.retrieve(loginInfo).flatMap {
              case Some(user) =>
                logger.warn(loginInfo.toString)
                Future.successful(Ok)
                silhouette.env.authenticatorService.create(loginInfo).flatMap {
                  authenticator => {
                    silhouette.env.eventBus.publish(LoginEvent(user, request))
                    silhouette.env.authenticatorService.init(authenticator).flatMap { v =>
                      silhouette.env.authenticatorService.embed(v, Ok)
                    }
                  }
                }
              case None => Future.successful(Forbidden(Json.obj("errorCode" -> "UserNotFound")))
            }
          }.recover {
            case _: InvalidPasswordException =>
              Forbidden(Json.obj("errorCode" -> "InvalidPasswordOrUser"))
            case _: IdentityNotFoundException =>
              Forbidden(Json.obj("errorCode" -> "InvalidPasswordOrUser"))
            case e =>
              logger.error(s"Sign in error email = ${data.userID}", e)
              InternalServerError(Json.obj("errorCode" -> "SystemError"))
          }
        }
      )
    }
  }

}
