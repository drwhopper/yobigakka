package models.daos

import com.mohiva.play.silhouette.api.LoginInfo
import models.User

import scala.concurrent.Future

trait UserDAO {

  def find(loginInfo: LoginInfo): Future[Option[User]]

  def findByUID(userID: String): Future[Option[User]]

  def save(user: User): Future[User]

}
