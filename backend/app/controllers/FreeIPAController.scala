package controllers


import play.api.libs.json.{JsValue, Json}
import play.api.mvc.{AbstractController, ControllerComponents}
import providers.FreeIPAProvider

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext



@Singleton
class FreeIPAController @Inject() (
    cc: ControllerComponents, 
    freeIPAProvider: FreeIPAProvider 
)(implicit ec: ExecutionContext) 
    extends AbstractController(cc) {


  def getUser(username: String) = Action.async {

    freeIPAProvider.getUser(username).map { responseJson =>
      val userJson = (responseJson \ "result" \ "result").asOpt[JsValue].getOrElse(Json.obj())

      Ok(userJson)
    } recover {
      case ex: Throwable =>
        InternalServerError(Json.obj("error" -> s"Unexpected error: ${ex.getMessage}"))
    }
  }
  

  def getGroup(groupname: String) = Action.async {
    freeIPAProvider.getGroup(groupname).map { responseJson =>
      val groupJson = (responseJson \ "result" \ "result").asOpt[JsValue].getOrElse(Json.obj())

      Ok(groupJson)
    } recover {
      case ex: Throwable =>
        InternalServerError(Json.obj("error" -> s"Unexpected error: ${ex.getMessage}"))
    }
  }

  
  def getGroups = Action.async {
    freeIPAProvider.getGroups.map { responseJson =>
      val groupsJson = (responseJson \ "result" \ "result").asOpt[JsValue].getOrElse(Json.obj())

      Ok(groupsJson) 
    } recover {
      case ex: Throwable =>
        InternalServerError(Json.obj("error" -> s"Unexpected error: ${ex.getMessage}"))
    }
  }

  def getAllUsers = Action.async {
    freeIPAProvider.getAllUsers.map { responseJson =>
      val usersJson = (responseJson \ "result" \ "result").asOpt[JsValue].getOrElse(Json.obj())

      Ok(usersJson)
    } recover {
      case ex: Throwable =>
        InternalServerError(Json.obj("error" -> s"Unexpected error: ${ex.getMessage}"))
    }
  }


}
