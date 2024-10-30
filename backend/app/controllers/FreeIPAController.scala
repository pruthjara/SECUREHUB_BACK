// app/controllers/FreeIPAController.scala
package controllers

import javax.inject._
import play.api.libs.json._
import play.api.mvc._

@Singleton
class FreeIPAController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  // Lista de usuarios ficticios simulando datos de FreeIPA
  private val fakeUsers = Json.arr(
    Json.obj("username" -> "hugopascual", "fullName" -> "Hugo Pascual", "email" -> "hugopascual@upm.es", "uid" -> 1001, "gid" -> 1001, "lastLogin" -> "2024-10-30T14:05:46Z", "status" -> "active"),
    Json.obj("username" -> "pruthjara", "fullName" -> "Pilar Jara", "email" -> "ruth.jperez@alumnos.upm.es", "uid" -> 1002, "gid" -> 1001, "lastLogin" -> "2024-10-29T11:25:35Z", "status" -> "inactive")
  )

  // Método para devolver los usuarios ficticios en JSON
  def getUsers: Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok(fakeUsers)
  }
}
