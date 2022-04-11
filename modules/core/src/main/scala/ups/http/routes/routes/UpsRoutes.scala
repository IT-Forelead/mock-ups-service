package ups.http.routes.routes

import cats.effect.Async
import cats.implicits._
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl
import org.http4s.server.Router
import ups.services.Ups

final case class UpsRoutes[F[_]: Async](ups: Ups[F]) extends Http4sDsl[F] {

  private[routes] val prefixPath = "/ups-service"

  private val httpRoutes: HttpRoutes[F] = HttpRoutes.of[F] {
    case _ @ POST -> Root / "update-tracing-number" =>
      ups.updateTrackingNumber().flatMap { response =>
        Ok(response)
      }
  }

  val routes: HttpRoutes[F] = Router(
    prefixPath -> httpRoutes
  )
}
