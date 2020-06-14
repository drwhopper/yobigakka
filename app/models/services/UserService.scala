package models.services

import com.mohiva.play.silhouette.api.LoginInfo
import com.mohiva.play.silhouette.api.services.IdentityService
import javax.inject.Inject
import models.User

import scala.concurrent.{ExecutionContext, Future}

/**
 * ユーザに対する操作
 */
class UserService @Inject()()(implicit ec: ExecutionContext) extends IdentityService[User] {

  /**
   * LoginInfoからユーザを見つけ出す
   * @param loginInfo loginInfo
   * @return
   */
  // TODO this is ハリボテ
  override def retrieve(loginInfo: LoginInfo): Future[Option[User]] = {
    loginInfo.providerKey match {
      case "Whopper" => Future.successful(Some(User("Whopper")))
      case _ => Future.successful(None)
    }
  }

  def createOrUpdate(loginInfo: LoginInfo, userID: String): Future[User] = {
    Future.successful(User("fresh"))
  }

}
