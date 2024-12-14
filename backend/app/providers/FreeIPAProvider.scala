package providers

// Importación de librerías necesarias para la inyección de dependencias, manejo de JSON y registro de logs
import javax.inject.{Inject, Singleton}
import play.api.libs.json.{JsValue, Json}
import play.api.Logging
import scala.concurrent.{ExecutionContext, Future}
import scala.sys.process._


// Definición de un proveedor como un singleton para manejar servicios relacionados con FreeIPA
@Singleton
class FreeIPAProvider @Inject()(implicit ec: ExecutionContext) extends Logging {
  // URL base para las solicitudes a FreeIPA
  private val freeIPAUrl = "https://freeipa.scrap.strast.es/ipa/session/json"