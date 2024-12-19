package controllers

// Importación de librerías necesarias para el manejo de JSON, controladores y dependencias
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.{AbstractController, ControllerComponents}
import providers.FreeIPAProvider

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext


// Definición de un controlador como un singleton, asegurando que haya solo una instancia en toda la aplicación
@Singleton
class FreeIPAController @Inject() (
    cc: ControllerComponents, // Inyección de los componentes del controlador (por ejemplo, acciones HTTP)
    freeIPAProvider: FreeIPAProvider // Inyección del proveedor de servicios FreeIPA
)(implicit ec: ExecutionContext) // Contexto de ejecución para manejar operaciones asíncronas
    extends AbstractController(cc) {

  // Método para obtener los detalles de un usuario dado un nombre de usuario
  def getUser(username: String) = Action.async {
    // Llamada al proveedor FreeIPA para obtener los datos del usuario
    freeIPAProvider.getUser(username).map { responseJson =>
      // Extraer la información del usuario desde la respuesta JSON
      val userJson = (responseJson \ "result" \ "result").asOpt[JsValue].getOrElse(Json.obj())

      Ok(userJson) // Devuelve el JSON completo del usuario como respuesta
    } recover {
      // Manejo de errores en caso de excepciones
      case ex: Throwable =>
        InternalServerError(Json.obj("error" -> s"Unexpected error: ${ex.getMessage}"))
    }
  }

  
  // Método para obtener todos los grupos disponibles en FreeIPA
  def getGroups = Action.async {
    // Llamada al proveedor FreeIPA para obtener los datos de los grupos
    freeIPAProvider.getGroups.map { responseJson =>
      // Extraer la información de los grupos desde la respuesta JSON
      val groupsJson = (responseJson \ "result" \ "result").asOpt[JsValue].getOrElse(Json.obj())

      Ok(groupsJson) // Devuelve el JSON completo de los grupos como respuesta
    } recover {
      // Manejo de errores en caso de excepciones
      case ex: Throwable =>
        InternalServerError(Json.obj("error" -> s"Unexpected error: ${ex.getMessage}"))
    }
  }

// Método para obtener todos los usuarios disponibles en FreeIPA
  def getAllUsers = Action.async {
    // Llamada al proveedor FreeIPA para obtener los datos de todos los usuarios
    freeIPAProvider.getAllUsers.map { responseJson =>
      // Extraer la información de los usuarios desde la respuesta JSON
      val usersJson = (responseJson \ "result" \ "result").asOpt[JsValue].getOrElse(Json.obj())

      Ok(usersJson) // Devuelve el JSON completo de los usuarios como respuesta
    } recover {
      // Manejo de errores en caso de excepciones
      case ex: Throwable =>
        InternalServerError(Json.obj("error" -> s"Unexpected error: ${ex.getMessage}"))
    }
  }


}
