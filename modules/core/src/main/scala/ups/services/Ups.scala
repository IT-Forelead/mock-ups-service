package ups.services
import cats.effect.Sync
import ups.domain.ResponseData._

trait Ups[F[_]] {
  def updateTrackingNumber(): F[ShipmentResponseData]
}

object Ups {
  def make[F[_]](implicit F: Sync[F]): Ups[F] =
    new Ups[F] {
      override def updateTrackingNumber(): F[ShipmentResponseData] = {
        F.delay(
          shipmentResponse
        )
      }
    }
}
