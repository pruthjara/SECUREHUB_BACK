package controllers

import javax.inject.Inject
import org.pac4j.play.PlayWebContext
import org.pac4j.play.store.PlaySessionStore
import org.pac4j.core.profile.ProfileManager
import org.pac4j.core.profile.CommonProfile
import play.api.mvc._
import security.SecurityConfig

import scala.jdk.CollectionConverters._ // Import correcto para Scala 2.13

class SecureController @Inject()(
    cc: ControllerComponents,
    playSessionStore: PlaySessionStore
) extends AbstractController(cc) {

  private val config = SecurityConfig.buildConfig()

  // Public endpoint: no requiere autenticación
  def publicEndpoint: Action[AnyContent] = Action {
    Ok("Este endpoint es público.")
  }

  // Secure endpoint: requiere autenticación
  def secureEndpoint: Action[AnyContent] = Action { implicit request =>
    // Crear el contexto web usando PlayWebContext
    val webContext = new PlayWebContext(request, playSessionStore)

    // Crear el ProfileManager
    val profileManager = new ProfileManager(webContext, playSessionStore)

    // Obtener perfiles disponibles y convertir a Scala
    val profiles = profileManager.getProfiles.asScala.toList

    profiles.headOption match {
      case Some(profile: CommonProfile) =>
        Ok(s"¡Acceso autorizado! Usuario: ${profile.getId}")
      case _ =>
        Unauthorized("No autorizado. Perfil no encontrado o token inválido.")
    }
  }
}
