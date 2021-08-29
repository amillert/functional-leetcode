ThisBuild / version := "0.0.1"
ThisBuild / organization := "amillert"
ThisBuild / scalaVersion := "2.13.5"

// lazy val libs = Seq(
//   "dev.zio"       %% "zio"          % "1.0.9",
//   "dev.zio"       %% "zio-test"     % "1.0.9" % "test",
//   "dev.zio"       %% "zio-test-sbt" % "1.0.9" % "test",
//   "org.scalatest" %% "scalatest"    % "3.2.3"
// )

// testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
lazy val day1 =
  project
    .in(file("./src/main/scala/pl/amillert/leetcode/day1"))

lazy val leetcode =
  project
    .in(file("."))
    .dependsOn(day1)
// .settings(libraryDependencies ++= libs)
