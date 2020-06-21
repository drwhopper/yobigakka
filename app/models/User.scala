package models

import com.mohiva.play.silhouette.api.{Identity, LoginInfo}
import play.api.libs.json.{Json, OFormat}

case class User(userID: String, loginInfo: LoginInfo) extends Identity

object User {
  implicit val jsonFormat: OFormat[User] = Json.format[User]
}