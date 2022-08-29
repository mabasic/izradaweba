ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "3.1.3"

lazy val root = (project in file("."))
  .settings(
    name := "izradaweba"
  )

lazy val js = (project in file("js"))
  .settings(
    name := "Scala.js Tutorial",
    // This is an application with a main method
    scalaJSUseMainModuleInitializer := true,
    libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "2.1.0",
    jsEnv := new org.scalajs.jsenv.jsdomnodejs.JSDOMNodeJSEnv(),
    libraryDependencies += "org.scalatest" %%% "scalatest" % "3.2.13" % "test"
  ).enablePlugins(ScalaJSPlugin)