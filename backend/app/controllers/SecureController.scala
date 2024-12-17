package controllers

import org.pac4j.play.scala.{SecureAction, SecureActionBuilder}
import play.api.mvc._
import javax.inject.Inject
import security.SecurityConfig

class SecureController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  // Instancia de SecureAction
  private val secureAction = new SecureActionBuilder(SecurityConfig.buildClients())

  // Endpoint protegido
  def secureEndpoint: Action[AnyContent] = secureAction { request =>
    Ok("¡Acceso autorizado! Has iniciado sesión correctamente.")
  }

  // Endpoint público
  def publicEndpoint: Action[AnyContent] = Action {
    Ok("Este endpoint es público.")
  }
}

