package ch.opentransportdata.ojp.data.dto.request.ser

import ch.opentransportdata.ojp.data.dto.OJP_NAME_SPACE
import ch.opentransportdata.ojp.data.dto.SIRI_NAME_SPACE
import ch.opentransportdata.ojp.data.dto.SIRI_PREFIX
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName
import java.time.LocalDateTime

/**
 * Created by Deniz Kalem on 20.05.2026
 */
@Serializable
@XmlSerialName("OJPStopEventRequest", OJP_NAME_SPACE, "")
internal data class StopEventRequestDto(

    @XmlElement(true)
    @XmlSerialName("RequestTimestamp", SIRI_NAME_SPACE, SIRI_PREFIX)
    @Contextual
    val requestTimestamp: LocalDateTime,

    @XmlElement(true)
    val location: LocationDto? = null,

    @XmlElement(true)
    @XmlSerialName("Params", OJP_NAME_SPACE, "")
    val params: StopEventParamsDto? = null,
)