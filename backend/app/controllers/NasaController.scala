package controllers

import javax.inject._
import play.api.mvc._
import play.api.libs.json._
import sttp.client3._
import scala.concurrent.{ExecutionContext, Future}
import play.api.Logger

@Singleton
class NasaController @Inject()(val controllerComponents: ControllerComponents)(implicit ec: ExecutionContext) extends BaseController {

  // Crea una instancia de Logger para esta clase
  val logger: Logger = Logger(this.getClass)

  val apiKey: String = "HhNUzFs82RGdatqLeMCfd67aoEhkgywmwtzIgpRf"
  val apiUrl: String = s"https://api.nasa.gov/planetary/apod?api_key=$apiKey"

  // Método para obtener el Astronomy Picture of the Day (APOD) usando STTP
  def getApod: Action[AnyContent] = Action.async {
    val request = basicRequest.get(uri"$apiUrl")

    // Backend para enviar la solicitud HTTP
    val backend = HttpURLConnectionBackend()

    // Ejecutar la solicitud de manera asíncrona
    val responseFuture: Future[Either[String, String]] = Future {
      val response = request.send(backend)
      response.body
    }

    // Procesar la respuesta y devolver un JSON al frontend
    responseFuture.map {
      case Right(data) =>
        logger.info(s"Response from NASA API: $data")  // Utiliza la instancia de logger

        // Verificar si la respuesta es un JSON válido
        try {
          val jsonResponse = Json.parse(data)  // Parsear la respuesta como JSON
          Ok(jsonResponse)                     // Devolver como JSON al frontend
        } catch {
          case ex: Exception =>
            logger.error(s"Error al parsear la respuesta JSON: ${ex.getMessage}")
            InternalServerError("Error al parsear la respuesta de la API de la NASA")
        }

      case Left(error) =>
        logger.error(s"Error al llamar a la API de la NASA: $error")
        InternalServerError(s"Error al llamar a la API de la NASA: $error")
    }.recover {
      case e: Exception =>
        logger.error(s"Error inesperado: ${e.getMessage}")
        InternalServerError(s"Error inesperado: ${e.getMessage}")
    }
  }
}
