package ups
import cats.effect.std.Supervisor
import cats.effect.{ IO, IOApp }
import org.typelevel.log4cats.slf4j.Slf4jLogger
import org.typelevel.log4cats.{ Logger, SelfAwareStructuredLogger }
import ups.config.ConfigLoader
import ups.modules.{ HttpApi, Services }
import ups.resources.MkHttpServer

object Application extends IOApp.Simple {

  implicit val logger: SelfAwareStructuredLogger[IO] = Slf4jLogger.getLogger[IO]

  override def run: IO[Unit] =
    ConfigLoader
      .load[IO]
      .flatMap { cfg =>
        Logger[IO].info(s"Loading config $cfg") >>
          Supervisor[IO].use { implicit sp =>
            val services = Services.make[IO]
            val api      = HttpApi.make[IO](cfg.logConfig, services).httpApp
            MkHttpServer[IO].newEmber(cfg.httpServerConfig, api).useForever
          }
      }
      .handleError { error =>
        Logger[IO].error(error)(s"Application crashed ....")
      }
}
