name := "api-perf-test-demo-1"

version := "1.0.0"

scalaVersion := "2.11.8"

enablePlugins(GatlingPlugin)

libraryDependencies ++= Seq(
    "io.gatling.highcharts" % "gatling-charts-highcharts" % "2.2.2" % "test",
    "io.gatling"            % "gatling-test-framework"    % "2.2.2" % "test"
  )
