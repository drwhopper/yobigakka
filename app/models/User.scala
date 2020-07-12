package models

import com.mohiva.play.silhouette.api.{Identity, LoginInfo}
import play.api.libs.json.{Json, OFormat}

case class User(var userID: String, var loginInfo: LoginInfo) extends Identity

object User {
  implicit val jsonFormat: OFormat[User] = Json.format[User]
}