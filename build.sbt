name := """yobigakka"""
organization := "red.drwp"
maintainer := "whopperisdelicious@gmail.com"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.2"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test

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