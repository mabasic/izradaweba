ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "3.2.0"
ThisBuild / organization := "eu.izradaweba"

ThisBuild / scalacOptions ++= Seq("-deprecation")

val http4sVersion = "1.0.0-M35"

lazy val root = (project in file("."))
  .settings(
    name := "Website",
    run / fork := true,
    libraryDependencies += "org.http4s" %% "http4s-dsl" % http4sVersion,
    libraryDependencies += "org.http4s" %% "http4s-ember-server" % http4sVersion,
    libraryDependencies += "org.http4s" %% "http4s-scalatags" % http4sVersion,
    libraryDependencies += "com.lihaoyi" %% "scalatags" % "0.12.0",
    libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.4.1",
    libraryDependencies += "org.http4s" %% "http4s-scalatags" % http4sVersion
  )

lazy val js = (project in file("js"))
  .settings(
    name := "Website JS",
    scalaJSUseMainModuleInitializer := true,
    libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "2.3.0",
    libraryDependencies += "org.scalatest" %%% "scalatest" % "3.2.13" % "test",
    jsEnv := new org.scalajs.jsenv.jsdomnodejs.JSDOMNodeJSEnv(),
    Compile / fastLinkJS / scalaJSLinkerOutputDirectory := baseDirectory.value / "../src/main/resources/js",
    Compile / fullLinkJS / scalaJSLinkerOutputDirectory := baseDirectory.value / "../src/main/resources/js"
  )
  .enablePlugins(ScalaJSPlugin)
  .disablePlugins(RevolverPlugin)
