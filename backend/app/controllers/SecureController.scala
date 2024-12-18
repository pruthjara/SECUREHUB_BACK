package controllers
import play.api.mvc._
import actions.KeycloakAuthActionBuilder 
import javax.inject._
import scala.concurrent.ExecutionContext

@Singleton
class SecureController @Inject()(cc: ControllerComponents)(implicit ec: ExecutionContext)
    extends AbstractController(cc) {

  private val keycloakAuth = new KeycloakAuthActionBuilder(parse.default, ec)

  def secureEndpoint: Action[AnyContent] = keycloakAuth { request =>
    Ok("You are authenticated!")
  }
}
