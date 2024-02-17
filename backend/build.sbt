name := """HottoCoffee"""
organization := "io.github.hottocoffee"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "3.3.1"

libraryDependencies ++= Seq(
  guice,
  evolutions,
  jdbc,

  "com.mysql" % "mysql-connector-j" % "8.2.0",

  "org.playframework.anorm" %% "anorm" % "2.7.0",

  "org.scalatestplus.play" %% "scalatestplus-play" % "6.0.0" % Test,
)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "io.github.hottocoffee.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "io.github.hottocoffee.binders._"
