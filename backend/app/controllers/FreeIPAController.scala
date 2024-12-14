package controllers

// Importación de librerías necesarias para el manejo de JSON, controladores y dependencias
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.{AbstractController, ControllerComponents}
import providers.FreeIPAProvider

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext
