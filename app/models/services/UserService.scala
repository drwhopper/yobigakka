package models.services

import com.mohiva.play.silhouette.api.LoginInfo
import com.mohiva.play.silhouette.api.services.IdentityService
import models.User

import scala.concurrent.Future

/**
 * ユーザに対する操作
 */
class UserService extends IdentityService[User] {

  /**
   * LoginInfoからユーザを見つけ出す
   * @param loginInfo loginInfo
   * @return
   */
  // TODO this is ハリボテ
  override def retrieve(loginInfo: LoginInfo): Future[Option[User]] = Future.successful(Some(User("")))
}
