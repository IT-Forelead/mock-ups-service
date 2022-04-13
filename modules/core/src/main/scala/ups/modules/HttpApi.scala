package ups.modules

import cats.effect._
import org.http4s.server.Router
import org.http4s.server.middleware._
import org.http4s.{HttpApp, HttpRoutes}
import org.typelevel.log4cats.Logger
import ups.config.LogConfig
import ups.http.routes.UpsRoutes

import scala.concurrent.duration.DurationInt

object HttpApi {
  def make[F[_]: Async: Logger](
      logConfig: LogConfig,
      services: Services[F]
  )(implicit F: Sync[F]) =
    new HttpApi[F](logConfig, services)
}

final class HttpApi[F[_]: Async: Logger: Sync] private (
    logConfig: LogConfig,
    services: Services[F]
) {

  private[this] val baseURL: String = "/"

  private[this] val upsRoutes = UpsRoutes[F](services.ups).routes

  private[this] val middleware: HttpRoutes[F] => HttpRoutes[F] = {
    { http: HttpRoutes[F] =>
      AutoSlash(http)
    } andThen { http: HttpRoutes[F] =>
      Timeout(60.seconds)(http)
    }
  }

  private[this] val loggers: HttpApp[F] => HttpApp[F] = {
    { http: HttpApp[F] =>
      RequestLogger.httpApp(logConfig.httpHeader, logConfig.httpBody)(http)
    } andThen { http: HttpApp[F] =>
      ResponseLogger.httpApp(logConfig.httpHeader, logConfig.httpBody)(http)
    }
  }

  private[this] val routes: HttpRoutes[F] = Router(
    baseURL -> upsRoutes
  )

  val httpApp: HttpApp[F] =
    loggers(middleware(routes).orNotFound)
}
