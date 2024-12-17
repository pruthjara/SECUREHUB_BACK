package security

import org.pac4j.core.client.Clients
import org.pac4j.play.store.PlaySessionStore
import org.pac4j.oidc.config.OidcConfiguration
import org.pac4j.oidc.client.OidcClient

object SecurityConfig {
  def buildClients(): Clients = {
    // Configuración de OIDC
    val oidcConfig = new OidcConfiguration()
    oidcConfig.setClientId("securehub-frontend")
    oidcConfig.setSecret("your-client-secret")
    oidcConfig.setDiscoveryURI("http://keycloak-service:8080/realms/securehub/.well-known/openid-configuration")
    oidcConfig.setUseNonce(true)

    val oidcClient = new OidcClient(oidcConfig)

    // Callback URL
    new Clients("http://frontend-service:3000/callback", oidcClient)
  }
}
