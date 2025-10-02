package ch.opentransportdata.ojp.data.dto.request.tir

import ch.opentransportdata.ojp.data.dto.OJP_NAME_SPACE
import ch.opentransportdata.ojp.data.dto.SIRI_NAME_SPACE
import ch.opentransportdata.ojp.data.dto.SIRI_PREFIX
import kotlinx.serialization.Contextual
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName
import java.time.LocalDateTime

/**
 * Created by Michael Ruppen on 07.06.2024
 */
@kotlinx.serialization.Serializable
@XmlSerialName("OJPTripRequest", OJP_NAME_SPACE, "")
internal data class TripRequestDto(

    @XmlElement(true)
    @XmlSerialName("RequestTimestamp", SIRI_NAME_SPACE, SIRI_PREFIX)
    @Contextual
    val requestTimestamp: LocalDateTime,

    @XmlElement(true)
    @XmlSerialName("Origin", OJP_NAME_SPACE, "")
    val origin: PlaceContextDto,

    @XmlElement(true)
    @XmlSerialName("Destination", OJP_NAME_SPACE, "")
    val destination: PlaceContextDto,

    @XmlElement(true)
    @XmlSerialName("Via", OJP_NAME_SPACE, "")
    val via: List<TripVia> = emptyList(),

    @XmlElement(true)
    @XmlSerialName("Params", OJP_NAME_SPACE, "")
    val params: TripParamsDto? = null
)