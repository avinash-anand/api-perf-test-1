import io.gatling.core.Predef._
import io.gatling.http.Predef._
import ApiRequest._

trait GetSimulation {

  val scn = scenario("Performance testing Get API")
    .exec(getApiRequest)
    .pause(5)


}

object ApiRequest {

  def getApiRequest = {
    http("Get API performance test")
      .get("/123456789")
      .check(status is 200)
  }

  def postApiRequest = {
    http("Post API performance test")
      .post("/")
      .formParam("", "")
      .check(status is 200)
  }

}
