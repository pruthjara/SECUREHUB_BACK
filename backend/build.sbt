name := """backend"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.14"

libraryDependencies ++= Seq(
  guice,  // Para inyección de dependencias
  "com.typesafe.play" %% "play-json" % "2.9.2",  // Para manejo de JSON
  "com.softwaremill.sttp.client3" %% "core" % "3.5.2",  // Para hacer peticiones HTTP
  "com.softwaremill.sttp.client3" %% "httpclient-backend" % "3.5.2",  // Backend HTTP para sttp
  "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.1" % Test  // Para pruebas
)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"

resolvers ++= Seq(
  "Maven Central" at "https://repo1.maven.org/maven2/",
  "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
)

