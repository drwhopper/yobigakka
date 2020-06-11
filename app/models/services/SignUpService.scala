package models.services

class SignUpService {

}

sealed trait SignUpResult
case object UserAlreadyExists extends SignUpResult
case class UserCreated(user: Unit) extends SignUpResult

case class CredentialsSingUpData(userID: String)