package models.services

import com.mohiva.play.silhouette.api.LoginInfo
import com.mohiva.play.silhouette.api.services.IdentityService
import javax.inject.Inject
import models.User

import scala.concurrent.{ExecutionContext, Future}

/**
 * ユーザに対する操作
 */
trait UserService extends IdentityService[User] {

  def createOrUpdate(loginInfo: LoginInfo, userID: String): Future[User]

}
