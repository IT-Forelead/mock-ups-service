package ups.services
import cats.effect.Sync

trait Ups[F[_]] {
  def updateTrackingNumber(): F[Unit]
}

object Ups {
  def make[F[_]](implicit F: Sync[F]): Ups[F] =
    new Ups[F] {
      override def updateTrackingNumber(): F[Unit] = F.unit
    }
}
