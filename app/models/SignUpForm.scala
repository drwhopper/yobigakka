package models

import models.services.CredentialsSingUpData
import play.api.data.Form
import play.api.data.Forms._

object SignUpForm {

  val form: Form[CredentialsSingUpData] = Form(
    mapping(
      "userID" -> nonEmptyText,
      "password" -> nonEmptyText
    )(CredentialsSingUpData.apply)(CredentialsSingUpData.unapply))

}
