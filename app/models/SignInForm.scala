package models

import play.api.data.Form
import play.api.data.Forms._

object SignInForm {

  val form: Form[Data] = Form(
    mapping(
      "userID" -> nonEmptyText,
      "password" -> nonEmptyText
    )(Data.apply)(Data.unapply))

  case class Data(userID: String, password: String)

}
