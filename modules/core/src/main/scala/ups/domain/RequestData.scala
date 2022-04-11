package ups.domain
import derevo.cats.show
import derevo.circe.magnolia.{ decoder, encoder }
import derevo.derive

object RequestData {
  @derive(decoder, encoder, show)
  case class ReturnService(code: String)

  @derive(decoder, encoder, show)
  case class Phone(number: String)

  @derive(decoder, encoder, show)
  case class Address(
      addressLine: String,
      city: String,
      stateProvinceCode: String,
      postalCode: String,
      countryCode: String
  )

  @derive(decoder, encoder, show)
  case class Shipper(name: String, attentionName: String, phone: Phone, shipperNumber: String, address: Address)

  @derive(decoder, encoder, show)
  case class ShipField(name: String, attentionName: String, phone: Phone, address: Address)

  @derive(decoder, encoder, show)
  case class BillShipper(accountNumber: String)

  @derive(decoder, encoder, show)
  case class ShipmentCharge(`type`: String, billShipper: BillShipper)

  @derive(decoder, encoder, show)
  case class PaymentInformation(shipmentCharge: ShipmentCharge)

  @derive(decoder, encoder, show)
  case class ShipmentService(code: String, description: String)

  @derive(decoder, encoder, show)
  case class Code(code: String)

  @derive(decoder, encoder, show)
  case class PackageWeight(unitOfMeasurement: Code, weight: String)

  @derive(decoder, encoder, show)
  case class ReferenceNumber(barCodeIndicator: String, value: String)

  @derive(decoder, encoder, show)
  case class PackageList(
      description: String,
      packaging: Code,
      packageWeight: PackageWeight,
      referenceNumber: ReferenceNumber
  )

  @derive(decoder, encoder, show)
  case class Shipment(
      Description: String,
      ReturnService: ReturnService,
      Shipper: Shipper,
      ShipTo: ShipField,
      ShipFrom: ShipField,
      PaymentInformation: PaymentInformation,
      Service: ShipmentService,
      Package: Seq[PackageList]
  )

  @derive(decoder, encoder, show)
  case class ShipmentRequest(shipment: Shipment)

  @derive(decoder, encoder, show)
  case class UpsRequest(shipmentRequest: ShipmentRequest)

}
