import Dependencies._
ThisBuild / version := "0.1"
ThisBuild / organization := "IT-Forelead"
ThisBuild / scalaVersion := "2.13.8"

lazy val root = (project in file("."))
  .settings(
    name := "mock-ups-service"
  )
  .aggregate(core)

lazy val core = (project in file("modules/core"))
  .settings(
    name := "mock-ups-service-core",
    scalacOptions ++= CompilerOptions.cOptions,
    coverageEnabled := true,
    scalafmtOnCompile := true,
    resolvers += Resolver.sonatypeRepo("snapshots"),
    Defaults.itSettings,
    libraryDependencies ++= coreLibraries
  )

lazy val tests = (project in file("modules/tests"))
  .configs(IntegrationTest)
  .settings(
    name := "mock-ups-service-suite",
    testFrameworks += new TestFramework("weaver.framework.CatsEffect"),
    scalacOptions ++= CompilerOptions.cOptions,
    libraryDependencies ++= testLibraries
  )
  .dependsOn(core)

val runServer = inputKey[Unit]("Runs server")

runServer := {
  (core / Compile / run).evaluated
}