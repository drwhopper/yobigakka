name := """yobigakka"""
organization := "red.drwp"
maintainer := "whopperisdelicious@gmail.com"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.2"

resolvers += "Atlassian's Maven Public Repository" at "https://packages.atlassian.com/maven-public/"

libraryDependencies ++= Seq(
  guice,
  "com.mohiva" %% "play-silhouette" % "7.0.0",
  "com.mohiva" %% "play-silhouette-password-bcrypt" % "7.0.0",
  "com.mohiva" %% "play-silhouette-crypto-jca" % "7.0.0",
  "com.mohiva" %% "play-silhouette-persistence" % "7.0.0",
  "com.lightbend.akka" %% "akka-stream-alpakka-orientdb" % "2.0.0",

  "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test,
  "com.mohiva" %% "play-silhouette-testkit" % "7.0.0" % Test
)

PlayKeys.playRunHooks += baseDirectory.map(NpmBuild.apply).value

lazy val frontEndBuild = taskKey[Unit]("Frontend Build")

val frontend = file("frontend")

frontEndBuild := {
  import NpmBuild._
  val logger = streams.value.log
  logger.info(s"Frontend Build in $frontend")
  println(Shell.executeWithMsg(Commands.install, frontend))
  println(Shell.executeWithMsg(Commands.build, frontend))
}

sources in (Compile, doc) := Seq.empty

dist := (dist dependsOn frontEndBuild).value
stage := (stage dependsOn dist).value
