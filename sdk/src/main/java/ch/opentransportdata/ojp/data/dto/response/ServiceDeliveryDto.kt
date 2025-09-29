package ch.opentransportdata.ojp.data.dto.response

import ch.opentransportdata.ojp.data.dto.OJP_NAME_SPACE
import ch.opentransportdata.ojp.data.dto.SIRI_NAME_SPACE
import ch.opentransportdata.ojp.data.dto.SIRI_PREFIX
import ch.opentransportdata.ojp.data.dto.converter.LocalDateTimeSerializer
import ch.opentransportdata.ojp.data.dto.response.delivery.AbstractDeliveryDto
import ch.opentransportdata.ojp.data.dto.response.delivery.LocationInformationDeliveryDto
import ch.opentransportdata.ojp.data.dto.response.delivery.TripDeliveryDto
import ch.opentransportdata.ojp.data.dto.response.delivery.TripRefineDeliveryDto
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName
import java.time.LocalDateTime

/**
 * Created by Michael Ruppen on 08.04.2024
 */
@Serializable
@XmlSerialName("ServiceDelivery", SIRI_NAME_SPACE, SIRI_PREFIX)
internal data class ServiceDeliveryDto(
    @XmlElement(true)
    @XmlSerialName("ResponseTimestamp", SIRI_NAME_SPACE, SIRI_PREFIX)
    @Serializable(with = LocalDateTimeSerializer::class)
    val responseTimestamp: LocalDateTime,

    @XmlElement(true)
    @XmlSerialName("ProducerRef", SIRI_NAME_SPACE, SIRI_PREFIX)
    val producerRef: String? = null,

    // Polymorphic child: tag name determines the concrete delivery subtype.
    // You can either rely on @XmlSerialName on the subtypes (with a polymorphic SerializersModule),
    // or explicitly list the element names here via @XmlPolyChildren:
    @XmlElement(true)
    @XmlSerialName("OJPLocationInformationDelivery", OJP_NAME_SPACE, "")
    val ojpLocationInformationDelivery: LocationInformationDeliveryDto? = null,

    @XmlElement(true)
    @XmlSerialName("OJPTripDelivery", OJP_NAME_SPACE, "")
    val ojpTripDelivery: TripDeliveryDto? = null,

    @XmlElement(true)
    @XmlSerialName("OJPTripRefineDelivery", OJP_NAME_SPACE, "")
    val ojpTripRefineDelivery: TripRefineDeliveryDto? = null,
//    @XmlElement(true)
//    @XmlPolyChildren(
//        value = [
//            // childSerialName[=[prefix:]localName]
//            "OJPLocationInformationDelivery=ojp:OJPLocationInformationDelivery",
//            "OJPTripDelivery=ojp:OJPTripDelivery",
//            "OJPTripRefineDelivery=ojp:OJPTripRefineDelivery"
//        ]
//    )
//    val ojpDelivery: AbstractDeliveryDto
) {
    // Optional: keep a convenience getter so the rest of your code hardly changes
    val ojpDelivery: AbstractDeliveryDto?
        get() = ojpLocationInformationDelivery
            ?: ojpTripDelivery
            ?: ojpTripRefineDelivery
}
