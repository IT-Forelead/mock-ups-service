package ups.modules
import cats.effect.Sync
import ups.services.Ups

object Services {
  def make[F[_]: Sync]: Services[F] = {
    new Services[F](
      ups = Ups.make[F]
    ) {}
  }
}

sealed abstract class Services[F[_]] private (
    val ups: Ups[F]
)
