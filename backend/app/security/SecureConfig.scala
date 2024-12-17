package security

import org.pac4j.core.client.Clients
import org.pac4j.oidc.config.OidcConfiguration
import org.pac4j.oidc.client.OidcClient

object SecurityConfig {
  def buildClients(): Clients = {
    val oidcConfig = new OidcConfiguration()
    oidcConfig.setClientId("securehub-frontend")
    oidcConfig.setSecret("your-client-secret")
    oidcConfig.setDiscoveryURI("http://keycloak-service:8080/realms/securehub/.well-known/openid-configuration")

    val oidcClient = new OidcClient(oidcConfig)
    new Clients("http://frontend-service:3000/callback", oidcClient)
  }
}
