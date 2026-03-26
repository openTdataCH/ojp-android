package ch.opentransportdata.ojp.data.dto.request.tir

import ch.opentransportdata.ojp.data.dto.OJP_NAME_SPACE
import ch.opentransportdata.ojp.data.dto.SIRI_NAME_SPACE
import ch.opentransportdata.ojp.data.dto.SIRI_PREFIX
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName
import java.time.LocalDateTime

/**
 * Created by Deniz Kalem on 25.03.2026
 */
@Serializable
@XmlSerialName("OJPTripInfoRequest", OJP_NAME_SPACE, "")
internal data class TripInfoRequestDto(

    @XmlElement(true)
    @XmlSerialName("RequestTimestamp", SIRI_NAME_SPACE, SIRI_PREFIX)
    @Contextual
    val requestTimestamp: LocalDateTime,

    @XmlElement(true)
    @XmlSerialName("JourneyRef", OJP_NAME_SPACE, "")
    val journeyRef: String,

    @XmlElement(true)
    @XmlSerialName("OperatingDayRef", OJP_NAME_SPACE, "")
    val operatingDayRef: String,

    @XmlElement(true)
    @XmlSerialName("Params", OJP_NAME_SPACE, "")
    val params: TripInfoParamsDto? = null,
)