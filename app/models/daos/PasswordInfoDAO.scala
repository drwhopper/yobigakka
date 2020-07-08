package models.daos

import com.mohiva.play.silhouette.api.{AuthInfo, LoginInfo}
import com.mohiva.play.silhouette.api.util.PasswordInfo
import com.mohiva.play.silhouette.persistence.daos.DelegableAuthInfoDAO
import javax.inject.Inject
import play.api.libs.json.{Format, JsObject, Json, OFormat}
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.play.json._
import reactivemongo.api.commands.WriteResult
import reactivemongo.play.json.collection.JSONCollection

import scala.concurrent.{ExecutionContext, Future}
import scala.reflect.ClassTag


/*
https://github.com/mohiva/play-silhouette-persistence-reactivemongo/blob/master/src/main/scala/com/mohiva/play/silhouette/persistence/daos/MongoAuthInfoDAO.scala
 */

class PasswordInfoDAO @Inject()(reactiveMongoApi: ReactiveMongoApi)(implicit ec: ExecutionContext, val classTag: ClassTag[PasswordInfo])
  extends DelegableAuthInfoDAO[PasswordInfo] {

  implicit val passwordInfoFormat: OFormat[PasswordInfo] = Json.format[PasswordInfo]

  def collection: Future[JSONCollection] = reactiveMongoApi.database.map(_.collection("authInfo"))

  override def find(loginInfo: LoginInfo): Future[Option[PasswordInfo]] = {
    val query = Json.obj("_id" -> loginInfo)
    collection.flatMap(_.find(query, projection = Some(Json.obj("_id" -> 0))).one[PasswordInfo])
  }

  override def add(loginInfo: LoginInfo, authInfo: PasswordInfo): Future[PasswordInfo] = {
    val query = merge(loginInfo, authInfo)
    onSuccess(collection.flatMap(_.insert(ordered=false).one(query)), authInfo)
  }

  override def update(loginInfo: LoginInfo, authInfo: PasswordInfo): Future[PasswordInfo] = ???

  override def save(loginInfo: LoginInfo, authInfo: PasswordInfo): Future[PasswordInfo] = ???

  override def remove(loginInfo: LoginInfo): Future[Unit] = ???

  private def merge(loginInfo: LoginInfo, authInfo: PasswordInfo): JsObject = {
    Json.obj("_id" -> loginInfo).deepMerge(Json.toJson(authInfo).as[JsObject])
  }

  private def onSuccess[T](result: Future[WriteResult], entity: T): Future[T] = result.recoverWith {
    case e => Future.failed(new Exception("Got exception from MongoDB", e.getCause))
  }.map { r =>
    WriteResult.lastError(r) match {
      case Some(e) => throw new Exception(e.message, e)
      case _ => entity
    }
  }
}
