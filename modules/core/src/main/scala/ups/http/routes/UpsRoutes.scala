package ups.http.routes

import cats.MonadThrow
import org.http4s.HttpRoutes
import org.http4s.circe.JsonDecoder
import org.http4s.dsl.Http4sDsl
import org.http4s.server.Router
import ups.modules.Services
import ups.services.Ups

final case class UpsRoutes[F[_]: JsonDecoder: MonadThrow](ups: Ups[F]) extends Http4sDsl[F] {

  private[routes] val prefixPath = "/ups-service"

  private val httpRoutes: HttpRoutes[F] = HttpRoutes.of[F] {
    case GET -> Root =>
      Ok("")
  }

  val routes: HttpRoutes[F] = Router(
    prefixPath -> httpRoutes
  )
}
