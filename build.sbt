ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "3.1.3"
ThisBuild / organization := "eu.izradaweba"

val http4sVersion = "1.0.0-M35"

lazy val root = (project in file("."))
  .settings(
    name := "Website",
    libraryDependencies ++= Seq(
      "org.http4s" %% "http4s-dsl" % http4sVersion,
      "org.http4s" %% "http4s-ember-server" % http4sVersion,
      "org.http4s" %% "http4s-scalatags" % http4sVersion,
      "com.lihaoyi" %% "scalatags" % "0.11.1",
      "ch.qos.logback" % "logback-classic" % "1.2.11",
    )
  )

import java.io.File

lazy val js = (project in file("js"))
  .settings(
    name := "Website JS",
    // This is an application with a main method
    scalaJSUseMainModuleInitializer := true,
    libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "2.3.0",
    libraryDependencies += "org.scalatest" %%% "scalatest" % "3.2.13" % "test",
    jsEnv := new org.scalajs.jsenv.jsdomnodejs.JSDOMNodeJSEnv(),
    Compile / fastLinkJS / scalaJSLinkerOutputDirectory := new File("src/main/resources/js"),
    Compile / fullLinkJS / scalaJSLinkerOutputDirectory := new File("src/main/resources/js")
  ).enablePlugins(ScalaJSPlugin)
