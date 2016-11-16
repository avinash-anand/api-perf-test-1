import io.gatling.core.Predef._
import io.gatling.http.Predef._
import ApiRequest._

trait GetSimulation {

  val scn = scenario("Performance testing Get API")
    .exec(getApiRequest)
    .pause(5)

}

trait PostSimulation {

  val scn = scenario("Performance testing Post API")
    .exec(postApiRequest)
    .pause(5)

}

trait HttpConfig {

  val baseUrl = "http://localhost:9000/registration"

  val headers = Map[String, String](
    "Environment" -> "dev"
  )

  val httpConf = http
    .baseURL(baseUrl)
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .headers(headers)
    .doNotTrackHeader("1")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Mozilla/5.0 (Windows NT 5.1; rv:31.0) Gecko/20100101 Firefox/31.0")
}

object ApiRequest {

  def getApiRequest = {
    http("Get API performance test")
      .get("/123456789")
      .check(status is 200)
  }

  def postApiRequest = {
    http("Post API performance test")
      .post("")
      .formParam("registrationId", "123456789")
      .formParam("status", "Approved")
      .formParam("registrationDate", "2016-10-24")
      .formParam("processingDate", "2016-10-25T09:30:47Z")
      .formParam("isActive", "true")
      .formParam("cost", "100.75")
      .check(status is 200)
  }

}
