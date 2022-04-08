package ups.config

import cats.effect.Async
import cats.implicits._
import ciris.refined.refTypeConfigDecoder
import ciris.{ ConfigValue, Effect, env }
import eu.timepit.refined.types.net.UserPortNumber
import eu.timepit.refined.types.string.NonEmptyString

object ConfigLoader {

  def httpLogConfig: ConfigValue[Effect, LogConfig] =
    (
      env("HTTP_HEADER_LOG").as[Boolean],
      env("HTTP_BODY_LOG").as[Boolean]
    ).parMapN(LogConfig.apply)

  def httpServerConfig: ConfigValue[Effect, HttpServerConfig] =
    (
      env("HTTP_HOST").as[NonEmptyString],
      env("HTTP_PORT").as[UserPortNumber]
    ).parMapN(HttpServerConfig.apply)

  def load[F[_]: Async]: F[AppConfig] =
    (
      httpLogConfig,
      httpServerConfig
    ).parMapN(AppConfig.apply).load[F]
}
