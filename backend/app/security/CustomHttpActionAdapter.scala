package security

import org.pac4j.core.context.WebContext
import org.pac4j.core.exception.http.HttpAction
import org.pac4j.core.http.adapter.HttpActionAdapter
import play.api.mvc.Results._

class CustomHttpActionAdapter extends HttpActionAdapter {
  override def adapt(action: HttpAction, context: WebContext): play.api.mvc.Result = {
    action.getCode match {
      case 401 => Unauthorized("Unauthorized: Invalid or missing credentials.")
      case 403 => Forbidden("Forbidden: You do not have permission.")
      case _   => Status(action.getCode)("Unexpected HTTP action.")
    }
  }
}

