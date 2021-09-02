ThisBuild / version := "0.0.1"
ThisBuild / organization := "amillert"
ThisBuild / scalaVersion := "2.13.5"

lazy val commonADT =
  project
    .in(file("./src/main/scala/pl/amillert/leetcode/common/adt"))

lazy val day1 =
  project
    .in(file("./src/main/scala/pl/amillert/leetcode/day1"))
    .dependsOn(commonADT)

lazy val day2 =
  project
    .in(file("./src/main/scala/pl/amillert/leetcode/day2"))
    .dependsOn(commonADT)

lazy val day3 =
  project
    .in(file("./src/main/scala/pl/amillert/leetcode/day3"))
    .dependsOn(commonADT)

lazy val leetcode =
  project
    .in(file("."))
    .dependsOn(commonADT, day1, day2, day3)
