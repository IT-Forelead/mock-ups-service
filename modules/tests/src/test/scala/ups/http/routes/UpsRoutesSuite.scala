package ups.http.routes

import cats.effect._
import org.http4s.Method.POST
import org.http4s.Status.Ok
import org.http4s.client.dsl.io._
import org.http4s.implicits.http4sLiteralsSyntax
import ups.domain.ResponseData
import ups.http.routes.routes.{UpsRoutes, deriveEntityEncoder}
import ups.services.Ups
import ups.stub_services.UpsStub
import ups.utils.Generators._
import ups.utils.HttpSuite

object UpsRoutesSuite extends HttpSuite {
  def updateTrackingNumber(): Ups[IO] = new UpsStub {
    override def updateTrackingNumber(): IO[ResponseData.ShipmentResponseData] = {
      IO.delay(ResponseData.shipmentResponse)
    }
  }

  test("update tracking number - success") {
    forall(shipmentRequestGen) { data =>
      val req    = POST(data, uri"/ups-service/update-tracing-number")
      val routes = UpsRoutes[IO](new UpsStub).routes
      expectHttpStatus(routes, req)(Ok)
    }
  }
}
