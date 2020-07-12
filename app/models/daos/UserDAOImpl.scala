package models.daos

import akka.stream.Materializer
import akka.stream.alpakka.orientdb._
import akka.stream.alpakka.orientdb.scaladsl._
import akka.stream.scaladsl.{Sink, Source}
import com.mohiva.play.silhouette.api.LoginInfo
import com.orientechnologies.orient.core.db.OPartitionedDatabasePool
import com.orientechnologies.orient.core.record.impl.ODocument
import javax.inject.Inject
import models.User
import play.api.libs.json._

import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.duration._

class UserDAOImpl @Inject()(implicit ex: ExecutionContext, oDatabase: OPartitionedDatabasePool, materializer: Materializer) extends UserDAO {


  val className = "owwfuser"

  /**
   * 認証情報からユーザ探し
   * @param loginInfo
   * @return
   */
  override def find(loginInfo: LoginInfo): Future[Option[User]] = {
    OrientDbSource.typed(className, OrientDbSourceSettings(oDatabase), classOf[User])
      .filter(_.oDocument.loginInfo == loginInfo)
      .map(_.oDocument)
      .runWith(Sink.headOption)
  }

  /**
   * ユーザIDからユーザ探し
   * @param userID
   * @return
   */
  override def findByUID(userID: String): Future[Option[User]] = {
    OrientDbSource.typed(className, OrientDbSourceSettings(oDatabase), classOf[User])
      .filter(_.oDocument.userID == userID)
      .map(_.oDocument)
      .runWith(Sink.headOption)
  }

  override def save(user: User): Future[User] = {
    println(user)
    Source.single(user)
      .map(OrientDbWriteMessage(_))
      .groupedWithin(10, 50.millis)
      .runWith(OrientDbSink.typed(className, OrientDbWriteSettings(oDatabase), classOf[User]))
    Future(user)
  }
}
