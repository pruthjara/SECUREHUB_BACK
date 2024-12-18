package actions
import java.security.KeyFactory
import java.security.spec.X509EncodedKeySpec
import java.util.Base64
import com.auth0.jwt.algorithms.Algorithm
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

class KeycloakAuthActionBuilder(val parser: BodyParser[AnyContent], val executionContext: ExecutionContext)
    extends ActionBuilder[Request, AnyContent] {

  // Clave pública en formato String (sin BEGIN/END PUBLIC KEY)
  private val publicKeyString: String =
    "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAw+vpjQfs5svU1rO5wmgHQ4Lx/wF6IFmiSSp+wsDq6wetTCjfKgWU8gc5VsqmQhLVCc7uQDje2/lKfUmrC9TQaccB5o7URG2xS1l2zgLRTk2t2lLkHwCg/8xte7TMkUIjWd40Muu/7LjE4CAQNlF8yE22mDO8FHWhsmbU9WVIB62z4szAaExJ391hRcb12Z15KSahVfrWX1ruMQrAgIiLpxemU/bxywSKN5AM3F32S6rcyISfmPfSFf8FUU33DnMz9cc+xkeKxYwf0dv1HFYF0dl0F1D6kKhWwun5/21eRsfTvcjC3ZdQCNeBmfXBhhF6VUfSJHVnFQXZNYzRKIonnQIDAQAB"

  // Convertir clave pública String a RSAPublicKey
  private val publicKey = {
    val keyBytes = Base64.getDecoder.decode(publicKeyString)
    val keySpec = new X509EncodedKeySpec(keyBytes)
    KeyFactory.getInstance("RSA").generatePublic(keySpec).asInstanceOf[java.security.interfaces.RSAPublicKey]
  }

  // Crear algoritmo RSA256 con la clave pública
  private val algorithm = Algorithm.RSA256(publicKey, null)

  // Método principal que valida el token y ejecuta la acción
  override def invokeBlock[A](request: Request[A], block: Request[A] => Future[Result]): Future[Result] = {
    val maybeAuthHeader = request.headers.get("Authorization")

    maybeAuthHeader match {
      case Some(token) if token.startsWith("Bearer ") =>
        val jwtToken = token.stripPrefix("Bearer ")
        try {
          // Validar el token usando el algoritmo configurado
          val decodedJWT = com.auth0.jwt.JWT.require(algorithm).build().verify(jwtToken)
          block(request) // Token válido, continuar
        } catch {
          case ex: Exception =>
            Future.successful(Results.Unauthorized(s"Invalid Token: ${ex.getMessage}"))
        }

      case _ =>
        Future.successful(Results.Unauthorized("Authorization token is missing or invalid"))
    }
  }
}
