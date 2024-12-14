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

  // Método privado para ejecutar un comando curl dado un payload
  private def executeCurl(payload: String): JsValue = {
    // Construye el comando curl como una secuencia
    val curlCommand = Seq(
      "curl",
      "--negotiate", // Autenticación Kerberos
      "-u", ":", // Usuario vacío para Kerberos
      "-k", // Ignora errores de certificado SSL
      "-X", "POST", // Método POST
      "-H", "Content-Type: application/json", // Cabecera para tipo de contenido
      "-H", s"Referer: $freeIPAUrl", // Referer para la solicitud
      "-d", payload, // Cuerpo de la solicitud
      freeIPAUrl // URL de destino
    )

    logger.info(s"Executing curl command: ${curlCommand.mkString(" ")}") // Registra el comando curl ejecutado
    val result = curlCommand.!! // Ejecuta el comando y obtiene el resultado
    logger.info(s"Received response: $result") // Registra la respuesta obtenida
    Json.parse(result) // Convierte la respuesta en un objeto JSON
  }

  // Método para obtener los detalles de un usuario dado un nombre de usuario
  def getUser(username: String): Future[JsValue] = Future {
    // Construye el payload JSON para la solicitud de detalles de usuario
    val payload =
      s"""{
         |  "method": "user_show",
         |  "params": [["$username"], {"all": true}],
         |  "version": "2.254"
         |}""".stripMargin

    executeCurl(payload) // Ejecuta el comando curl con el payload
  }