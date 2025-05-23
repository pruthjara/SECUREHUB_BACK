package providers

import javax.inject.{Inject, Singleton}
import play.api.libs.json.{JsValue, Json}
import play.api.Logging
import scala.concurrent.{ExecutionContext, Future}
import scala.sys.process._

@Singleton
class FreeIPAProvider @Inject()(implicit ec: ExecutionContext) extends Logging {
  private val freeIPAUrl = "https://freeipa.andion.eu/ipa/session/json"

  private def executeCurl(payload: String): JsValue = {
    val curlCommand = Seq(
      "curl",
      "--negotiate", 
      "-u", ":", 
      "-k", 
      "-X", "POST", 
      "-H", "Content-Type: application/json", 
      "-H", s"Referer: $freeIPAUrl", 
      "-d", payload, 
      freeIPAUrl 
    )

    logger.info(s"Executing curl command: ${curlCommand.mkString(" ")}") 
    val result = curlCommand.!! 
    logger.info(s"Received response: $result") 
    Json.parse(result) 
  }

  def getUser(username: String): Future[JsValue] = Future {
    val payload =
      s"""{
         |  "method": "user_show",
         |  "params": [["$username"], {"all": true}],
         |  "version": "2.254"
         |}""".stripMargin

    executeCurl(payload)
  }

 
  def getGroups: Future[JsValue] = Future {
    val payload =
      s"""{
         |  "method": "group_find",
         |  "params": [[], {"all": true}],
         |  "version": "2.254"
         |}""".stripMargin

    executeCurl(payload) 
  }


  def getAllUsers: Future[JsValue] = Future {
   
    val payload =
      s"""{
         |  "method": "user_find",
         |  "params": [[], {"all": true}],
         |  "version": "2.254"
         |}""".stripMargin

    executeCurl(payload)
  }


  def getGroup(groupname: String): Future[JsValue] = Future {
 
    val payload =
      s"""{
        |  "method": "group_show",
        |  "params": [["$groupname"], {"all": true}],
        |  "version": "2.254"
        |}""".stripMargin

    executeCurl(payload) 
  }

}
