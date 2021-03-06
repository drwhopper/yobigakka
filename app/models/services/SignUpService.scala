package models.services

import com.mohiva.play.silhouette.api.LoginInfo
import com.mohiva.play.silhouette.api.repositories.AuthInfoRepository
import com.mohiva.play.silhouette.api.util.PasswordHasherRegistry
import com.mohiva.play.silhouette.impl.providers.CredentialsProvider
import javax.inject.Inject
import models.User

import scala.concurrent.{ExecutionContext, Future}

class SignUpService @Inject()(userService: UserService, passwordHasherRegistry: PasswordHasherRegistry,
                              authInfoRepository: AuthInfoRepository)(implicit ex: ExecutionContext) {
  def signUpByCredentials(data: CredentialsSingUpData): Future[SignUpResult] = {
    // userID を ID として使う
    val loginInfo = LoginInfo(CredentialsProvider.ID, data.userID)
    userService.retrieve(loginInfo).flatMap {
      case Some(user) => Future.successful(UserAlreadyExists)
      case None =>
        val authInfo = passwordHasherRegistry.current.hash(data.password)
        for {
          user <- userService.createOrUpdate(loginInfo, data.userID)
          authInfo <- authInfoRepository.add(loginInfo, authInfo)
        } yield {
          UserCreated(user)
        }
    }
  }
}

sealed trait SignUpResult

case object UserAlreadyExists extends SignUpResult

case class UserCreated(user: User) extends SignUpResult

case class CredentialsSingUpData(userID: String,
                                 password: String)