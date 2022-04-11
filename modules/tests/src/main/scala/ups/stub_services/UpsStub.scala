package ups.stub_services
import cats.effect._
import ups.domain.ResponseData.{ShipmentResponseData, shipmentResponse}
import ups.services.Ups

class UpsStub extends Ups[IO] {
  override def updateTrackingNumber(): IO[ShipmentResponseData] = IO.delay(shipmentResponse)
}
