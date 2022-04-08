import Dependencies.Libraries.{cats, catsEffect, log4cats, logback, _}
import sbt._
object Dependencies {

  object Versions {
    val http4s     = "0.23.11"
    val cats       = "2.7.0"
    val catsEffect = "3.3.11"
    val circe      = "0.15.0-M1"
    val ciris      = "2.0.1"
    val refined    = "0.9.27"
    val log4cats   = "2.2.0"
    val logback    = "1.2.11"
    val betterMonadicFor = "0.3.1"
    val kindProjector    = "0.13.2"
    val organizeImports  = "0.6.0"
    val weaver = "0.7.11"
  }

  object Libraries {
    def circe(artifact: String): ModuleID   = "io.circe"   %% artifact % Versions.circe
    def ciris(artifact: String): ModuleID   = "is.cir"     %% artifact % Versions.ciris
    def http4s(artifact: String): ModuleID  = "org.http4s" %% artifact % Versions.http4s
    def refined(artifact: String): ModuleID = "eu.timepit" %% artifact % Versions.refined

    val http4sDsl    = http4s("http4s-dsl")
    val http4sCore   = http4s("http4s-core")
    val http4sServer = http4s("http4s-ember-server")
    val http4sClient = http4s("http4s-ember-client")
    val http4sCirce  = http4s("http4s-circe")

    val circeCore    = circe("circe-core")
    val circeGeneric = circe("circe-generic")
    val circeParser  = circe("circe-parser")
    val circeRefined = circe("circe-refined")

    val cirisCore    = ciris("ciris")
    val cirisRefined = ciris("ciris-refined")

    val cats       = "org.typelevel" %% "cats-core"   % Versions.cats
    val catsEffect = "org.typelevel" %% "cats-effect" % Versions.catsEffect

    val log4cats = "org.typelevel" %% "log4cats-slf4j"  % Versions.log4cats
    val logback  = "ch.qos.logback" % "logback-classic" % Versions.logback

    val refinedType = refined("refined")

    // Test
    val catsLaws          = "org.typelevel"       %% "cats-laws"          % Versions.cats
    val log4catsNoOp      = "org.typelevel"       %% "log4cats-noop"      % Versions.log4cats
    val refinedScalacheck = "eu.timepit"          %% "refined-scalacheck" % Versions.refined
    val weaverCats        = "com.disneystreaming" %% "weaver-cats"        % Versions.weaver
    val weaverDiscipline  = "com.disneystreaming" %% "weaver-discipline"  % Versions.weaver
    val weaverScalaCheck  = "com.disneystreaming" %% "weaver-scalacheck"  % Versions.weaver
  }

  val catsLibs   = Seq(cats, catsEffect)
  val circeLibs  = Seq(circeCore, circeGeneric, circeParser, circeRefined)
  val cirisLibs  = Seq(cirisRefined, cirisCore)
  val http4sLibs = Seq(http4sDsl, http4sCore, http4sServer, http4sClient, http4sCirce)
  val logLibs    = Seq(log4cats, logback)

  val coreLibraries: Seq[ModuleID] = catsLibs ++ cirisLibs ++ circeLibs ++ http4sLibs ++ logLibs ++ Seq(
    refinedType
  )

  val testLibraries = Seq(
    log4catsNoOp,
    refinedScalacheck,
    weaverCats,
    weaverDiscipline,
    weaverScalaCheck
  )

}
