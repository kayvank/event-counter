import TodoListPlugin._

lazy val root = (project in file("."))
  .settings(
  organization := "q2io",
  name := "event-counter",
  scalaVersion := "2.12.8",
  scalacOptions in ThisBuild ++= Seq(
    "-language:_",
    "-Ypartial-unification",
    "-Ywarn-dead-code",
    "-Ywarn-numeric-widen",
    "-Xfuture",
    "-deprecation",
    "-encoding", "UTF-8",
    "-feature",
    "-unchecked"
  ),
  libraryDependencies ++= {
      object V {
        val specs2_check = "4.4.1"
        val logback = "1.2.3"
        val scalalogging = "3.9.0"
        val config = "1.3.2"
        val scalaz="7.2.26"
      }
      Seq(
        "org.scalaz" %% "scalaz-core" % V.scalaz,
//        "eu.timepit" %% "refined-scalaz" % "0.9.2",
        "org.scalaz" %% "scalaz-zio" % "0.11",
        "com.github.mpilquist" %% "simulacrum" % "0.15.0",
        "org.specs2" %% "specs2-scalacheck" % V.specs2_check % "test",
        "org.typelevel" %% "scalaz-specs2" % "0.5.2" % "test",
        "com.typesafe" %  "config" % V.config,
        "com.typesafe.scala-logging" %% "scala-logging" % V.scalalogging,
        "ch.qos.logback" % "logback-classic" % V.logback
      )})

withTodolistSettings(compile, Compile)

excludeFilter in unmanagedSources := HiddenFileFilter || "#*"

// addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)
