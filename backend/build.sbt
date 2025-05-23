name := "backend"
organization := "com.example"
version := "1.0-SNAPSHOT"

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)

scalaVersion := "2.13.14"

libraryDependencies ++= Seq(
  guice,
  "com.typesafe.play" %% "play-json" % "2.9.4",
  "com.typesafe.play" %% "play-ws" % "2.9.4",
  "org.asynchttpclient" % "async-http-client" % "2.12.3",
  "commons-codec" % "commons-codec" % "1.15",
  "org.apache.httpcomponents.client5" % "httpclient5" % "5.2.1"
)



resolvers += Resolver.mavenCentral


