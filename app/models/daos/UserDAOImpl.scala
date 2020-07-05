package models.daos

import com.mohiva.play.silhouette.api.LoginInfo
import javax.inject.Inject
import models.User
import play.api.libs.json._
import play.modules.reactivemongo._
import reactivemongo.play.json.compat._
import reactivemongo.play.json.collection._

import scala.concurrent.{ExecutionContext, Future}

class UserDAOImpl @Inject()(implicit ex: ExecutionContext, reactiveMongoApi: ReactiveMongoApi) extends UserDAO {

  def collection: Future[JSONCollection] = reactiveMongoApi.database.map(_.collection("user"))

  /**
   * 認証情報からユーザ探し
   * @param loginInfo
   * @return
   */
  override def find(loginInfo: LoginInfo): Future[Option[User]] = {
    val query = Json.obj("loginInfo" -> loginInfo)
    collection.flatMap(_.find(query, Option.empty[JsObject]).one[User])
  }

  /**
   * ユーザIDからユーザ探し
   * @param userID
   * @return
   */
  override def findByUID(userID: String): Future[Option[User]] = {
    val query = Json.obj("userID" -> userID)
    collection.flatMap(_.find(query, Option.empty[JsObject]).one[User])
  }

  override def save(user: User): Future[User] = {
    val query = Json.obj("userID" -> user.userID)
    collection.flatMap(_.update.one(query, user, upsert = true))
    Future.successful(user)
  }
}
