package providers

// Importación de librerías necesarias para la inyección de dependencias, manejo de JSON y registro de logs
import javax.inject.{Inject, Singleton}
import play.api.libs.json.{JsValue, Json}
import play.api.Logging
import scala.concurrent.{ExecutionContext, Future}
import scala.sys.process._