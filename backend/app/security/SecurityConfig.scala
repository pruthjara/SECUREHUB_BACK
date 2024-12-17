package security

import org.pac4j.core.client.Clients
import org.pac4j.oidc.config.OidcConfiguration
import org.pac4j.oidc.client.OidcClient
import org.pac4j.core.config.Config

object SecurityConfig {
  def buildConfig(): Config = {
    val oidcConfig = new OidcConfiguration()
    oidcConfig.setClientId("securehub-frontend")
    oidcConfig.setSecret("z9Ufuhld7XJRgTIcPODSg6uhoRQpAkt6")
    oidcConfig.setDiscoveryURI("http://192.168.100.3:8080/realms/securehub/.well-known/openid-configuration")

    val oidcClient = new OidcClient(oidcConfig)
    val clients = new Clients("http://192.168.100.109:9000/callback", oidcClient)

    new Config(clients)
  }
}
