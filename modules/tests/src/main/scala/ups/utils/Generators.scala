package ups.utils

import org.scalacheck.Gen
import ups.domain.RequestData.{BillShipper, PaymentInformation, ReferenceNumber, ShipField, _}

object Generators {

  val nonEmptyStringGen: Gen[String] =
    Gen
      .chooseNum(21, 40)
      .flatMap { n =>
        Gen.buildableOfN[String, Char](n, Gen.alphaChar)
      }

  val numberGen: Gen[String] =
    Gen.numChar.map { num =>
      num.toString
    }

  val returnServiceGen: Gen[ReturnService] =
    nonEmptyStringGen.map { code =>
      ReturnService(code = code)
    }

  val phoneGen: Gen[Phone] =
    numberGen.map { phone =>
      Phone(number = phone)
    }

  val addressGen: Gen[Address] =
    for {
      address <- nonEmptyStringGen
      city <- nonEmptyStringGen
      state <- nonEmptyStringGen
      postalCode <- nonEmptyStringGen
      countryCode <- nonEmptyStringGen
    } yield Address(
      addressLine = address,
      city = city,
      stateProvinceCode = state,
      postalCode = postalCode,
      countryCode = countryCode
    )

  val shipperGen: Gen[Shipper] =
    for {
      name <- nonEmptyStringGen
      attentionName <- nonEmptyStringGen
      phone <- phoneGen
      shipperNumber <- numberGen
      address <- addressGen
    } yield Shipper(
      name = name,
      attentionName = attentionName,
      phone = phone,
      shipperNumber = shipperNumber,
      address = address
    )

  val shipFieldGen: Gen[ShipField] =
    for {
      name <- nonEmptyStringGen
      attentionName <- nonEmptyStringGen
      phone <- phoneGen
      address <- addressGen
    } yield ShipField(
      name = name,
      attentionName = attentionName,
      phone = phone,
      address = address
    )

  val billShipperGen: Gen[BillShipper] =
    numberGen.map { number =>
      BillShipper(accountNumber = number)
    }

  val shipmentChargeGen: Gen[ShipmentCharge] =
    for {
      shipmentType <- nonEmptyStringGen
      billShipper <- billShipperGen
    } yield ShipmentCharge(`type` = shipmentType, billShipper = billShipper)

  val paymentInformationGen: Gen[PaymentInformation] =
    shipmentChargeGen.map { shipmentCharge =>
      PaymentInformation(shipmentCharge = shipmentCharge)
    }

  val shipmentServiceGen: Gen[ShipmentService] =
    for {
      code <- nonEmptyStringGen
      description <- nonEmptyStringGen
    } yield ShipmentService(code = code, description = description)

  val codeGen: Gen[Code] =
    numberGen.map { number =>
      Code(code = number)
    }

  val packageWeightGen: Gen[PackageWeight] =
    for {
      weight <- nonEmptyStringGen
      unitOfMeasurement <- codeGen
    } yield PackageWeight(unitOfMeasurement = unitOfMeasurement, weight = weight)

  val referenceNumberGen: Gen[ReferenceNumber] =
    for {
      barCodeIndicator <- nonEmptyStringGen
      value <- nonEmptyStringGen
    } yield ReferenceNumber(barCodeIndicator = barCodeIndicator, value = value)

  val packageListGen: Gen[PackageList] =
    for {
      description <- nonEmptyStringGen
      packaging <- codeGen
      packageWeight <- packageWeightGen
      referenceNumber <- referenceNumberGen
    } yield PackageList(
      description = description,
      packaging = packaging,
      packageWeight = packageWeight,
      referenceNumber = referenceNumber
    )

  val shipmentGen: Gen[Shipment] =
    for {
      description <- nonEmptyStringGen
      returnService <- returnServiceGen
      shipper <- shipperGen
      shipTo <- shipFieldGen
      shipFrom <- shipFieldGen
      paymentInformation <- paymentInformationGen
      shipmentService <- shipmentServiceGen
      packageList <- packageListGen
    } yield Shipment(
      Description = description,
      ReturnService = returnService,
      Shipper = shipper,
      ShipTo = shipTo,
      ShipFrom = shipFrom,
      PaymentInformation = paymentInformation,
      Service = shipmentService,
      Package = Seq(packageList)
    )

  val shipmentRequestGen: Gen[ShipmentRequest] =
    shipmentGen.map { shipment =>
      ShipmentRequest(shipment = shipment)
    }

}
