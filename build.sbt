ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "3.1.3"
ThisBuild / organization := "eu.izradaweba"

val http4sVersion = "1.0.0-M36"

lazy val root = (project in file("."))
  .settings(
    name := "Website",
    libraryDependencies ++= Seq(
      "org.http4s" %% "http4s-dsl" % "0.23.15",
      "org.http4s" %% "http4s-ember-server" % "0.23.15",
      "org.http4s" %% "http4s-scalatags" % "0.24.0",
      "com.lihaoyi" %% "scalatags" % "0.11.1",
      "ch.qos.logback" % "logback-classic" % "1.2.11",
    )
  )

lazy val js = (project in file("js"))
  .settings(
    name := "Website JS",
    // This is an application with a main method
    scalaJSUseMainModuleInitializer := true,
    libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "2.3.0",
    libraryDependencies += "org.scalatest" %%% "scalatest" % "3.2.13" % "test",
    jsEnv := new org.scalajs.jsenv.jsdomnodejs.JSDOMNodeJSEnv()
  ).enablePlugins(ScalaJSPlugin)