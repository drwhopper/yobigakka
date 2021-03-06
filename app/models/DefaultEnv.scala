package models

import com.mohiva.play.silhouette.api.Env
import com.mohiva.play.silhouette.impl.authenticators.JWTAuthenticator

trait DefaultEnv extends Env {
  type I = User
  type A = JWTAuthenticator
}
