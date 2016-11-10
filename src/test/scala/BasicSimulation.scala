import io.gatling.core.Predef._
import io.gatling.core.controller.inject.InjectionStep
import io.gatling.http.Predef._

class BasicSimulation extends Simulation {

  val baseUrl = "http://localhost:9000/registration"

  val httpConf = http
    .baseURL(baseUrl)
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .doNotTrackHeader("1")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Mozilla/5.0 (Windows NT 5.1; rv:31.0) Gecko/20100101 Firefox/31.0")

//  def runSimulation(iss: InjectionStep*) = {
//    setUp(scn.inject(iss:_*)).protocols(httpConf).assertions(global.failedRequests.count.is(0))
//  }

  val scn = scenario("BasicSimulation")
    .exec(
      http("request_1")
        .get("/123456789")
        .check(status is 200)
    )
    .pause(5)

  setUp(
    scn.inject(atOnceUsers(1))
  ).protocols(httpConf).assertions(global.failedRequests.count.is(0))

}
