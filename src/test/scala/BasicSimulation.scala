import io.gatling.core.Predef._
import io.gatling.core.controller.inject.InjectionStep
import io.gatling.http.Predef._
import scala.concurrent.duration._

trait BasicSimulationConf extends Simulation with HttpConfig {

  val scns = Seq(new GetSimulation {}.scn, new PostSimulation {}.scn)

  val first = group(scns.head.name)(exec(scns.head))
  val chain = scns.tail.foldLeft(first)((chainBuilder, scenario) => chainBuilder.group(scenario.name)(exec(chainBuilder)))

  def runSimulation(iss: InjectionStep*) = {

    val injectedBuilders = scns.map { a =>
      a.inject(iss: _*)
    }

    setUp(
      injectedBuilders: _*
    ).protocols(httpConf)
      .assertions(global.failedRequests.count.is(0))
//      .assertions(global.responseTime.mean.lessThan(1))
  }

}

class GetWith1User extends BasicSimulationConf {
  runSimulation(atOnceUsers(1))
}

class GetWithRampBuilder extends BasicSimulationConf {
  runSimulation(
//    RampBuilder(25).over(5 minutes)
        RampBuilder(50).over(10 seconds)
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

