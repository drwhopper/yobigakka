package models.services

import com.mohiva.play.silhouette.api.LoginInfo
import javax.inject.Inject
import models.User
import models.daos.UserDAO

import scala.concurrent.{ExecutionContext, Future}

class UserServiceImpl @Inject()(userDAO: UserDAO)(implicit ec: ExecutionContext) extends UserService {
  /**
   * LoginInfoからユーザを見つけ出す
   *
   * @param loginInfo loginInfo
   * @return
   */
  def retrieve(loginInfo: LoginInfo): Future[Option[User]] = userDAO.find(loginInfo)

  def createOrUpdate(loginInfo: LoginInfo, userID: String): Future[User] = {

    Future.sequence(Seq(userDAO.find(loginInfo), userDAO.findByUID(userID))).flatMap { users =>
      users.flatten.headOption match {
        case Some(user) =>
          userDAO.save(user.copy())
        case None =>
          userDAO.save(User(userID, loginInfo))
      }
    }
  }

}
