ThisBuild / version := "0.0.1"
ThisBuild / organization := "amillert"
ThisBuild / scalaVersion := "2.13.5"

lazy val day1 =
  project
    .in(file("./src/main/scala/pl/amillert/leetcode/day1"))

lazy val day2 =
  project
    .in(file("./src/main/scala/pl/amillert/leetcode/day2"))

lazy val leetcode =
  project
    .in(file("."))
    .dependsOn(day1, day2)
