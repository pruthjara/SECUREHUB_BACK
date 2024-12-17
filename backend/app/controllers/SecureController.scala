package controllers

import org.pac4j.play.scala.Security
import org.pac4j.play.store.PlaySessionStore
import play.api.mvc._
import security.SecurityConfig

import javax.inject.Inject

class SecureController @Inject() (cc: ControllerComponents, sessionStore: PlaySessionStore)
  extends AbstractController(cc) {

  // Middleware para proteger rutas con pac4j
  val securedAction = new Security[PlaySessionStore](SecurityConfig.buildClients(), sessionStore)

  def secureEndpoint = securedAction { request =>
    Ok("¡Acceso autorizado! Has iniciado sesión correctamente.")
  }

  def publicEndpoint = Action {
    Ok("Este endpoint es público.")
  }
}
