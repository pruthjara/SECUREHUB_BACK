package controllers

import org.pac4j.play.scala.SecurityAction
import play.api.mvc._
import javax.inject.Inject
import security.SecurityConfig

class SecureController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  // Crea una instancia de SecurityAction
  private val securityAction = new SecurityAction[play.api.mvc.AnyContent](
    clients = SecurityConfig.buildClients()
  )

  // Endpoint protegido
  def secureEndpoint: Action[AnyContent] = securityAction { request =>
    Ok("¡Acceso autorizado! Has iniciado sesión correctamente.")
  }

  // Endpoint público
  def publicEndpoint: Action[AnyContent] = Action {
    Ok("Este endpoint es público.")
  }
}

