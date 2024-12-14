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
