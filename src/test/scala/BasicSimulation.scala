import io.gatling.core.Predef._
import io.gatling.core.controller.inject.InjectionStep
import io.gatling.http.Predef._
import scala.concurrent.duration._

trait BasicSimulationConf extends Simulation with GetSimulation {

  val baseUrl = "http://localhost:9000/registration"

  val httpConf = http
    .baseURL(baseUrl)
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .doNotTrackHeader("1")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Mozilla/5.0 (Windows NT 5.1; rv:31.0) Gecko/20100101 Firefox/31.0")

  def runSimulation(iss: InjectionStep*) = {
    setUp(scn.inject(iss:_*)).protocols(httpConf)
      .assertions(global.failedRequests.count.is(0))
//      .assertions(global.responseTime.mean.lessThan(1))
  }

}

class GetWith1User extends BasicSimulationConf {
  runSimulation(atOnceUsers(1))
}

class GetWithRampBuilder extends BasicSimulationConf {
  runSimulation(
    RampBuilder(25).over(5 minutes)
//    RampBuilder(500).over(10 seconds)
  )
}

class GetWithRampUserPerSec extends BasicSimulationConf {
  runSimulation(
    rampUsersPerSec(1.0D).to(2.0D).during(5 minutes)
//    rampUsersPerSec(1.0D).to(2.0D).during(15 seconds)
  )
}

class GetWithConstantUserPerSec extends BasicSimulationConf {
  runSimulation(
    constantUsersPerSec(10.0D).during(5 minutes)
//    constantUsersPerSec(10.0D).during(10 seconds)
  )
}

