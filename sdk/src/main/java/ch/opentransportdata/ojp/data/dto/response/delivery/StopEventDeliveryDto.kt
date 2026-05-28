package ch.opentransportdata.ojp.data.dto.response.delivery

import ch.opentransportdata.ojp.data.dto.OJP_NAME_SPACE
import ch.opentransportdata.ojp.data.dto.SIRI_NAME_SPACE
import ch.opentransportdata.ojp.data.dto.SIRI_PREFIX
import ch.opentransportdata.ojp.data.dto.response.ser.StopEventResponseContextDto
import ch.opentransportdata.ojp.data.dto.response.ser.StopEventResultDto
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName
import java.time.LocalDateTime

/**
 * Created by Deniz Kalem on 20.05.2026
 */
@Serializable
@XmlSerialName("OJPStopEventDelivery", OJP_NAME_SPACE, "")
data class StopEventDeliveryDto(
    @XmlElement(true)
    @XmlSerialName("ResponseTimestamp", SIRI_NAME_SPACE, SIRI_PREFIX)
    @Contextual
    override val responseTimestamp: LocalDateTime,

    @XmlElement(true)
    @XmlSerialName("RequestMessageRef", SIRI_NAME_SPACE, SIRI_PREFIX)
    override val requestMessageRef: String? = null,

    @XmlElement(true)
    @XmlSerialName("DefaultLanguage", SIRI_NAME_SPACE, SIRI_PREFIX)
    override val defaultLanguage: String? = null,

    @XmlElement(true)
    @XmlSerialName("Status", SIRI_NAME_SPACE, SIRI_PREFIX)
    val status: Boolean? = null,

    @XmlElement(true)
    @XmlSerialName("CalcTime", OJP_NAME_SPACE, "")
    val calcTime: Int? = null,

    @XmlElement(true)
    @XmlSerialName("StopEventResponseContext", OJP_NAME_SPACE, "")
    val responseContext: StopEventResponseContextDto? = null,

    @XmlElement(true)
    @XmlSerialName("StopEventResult", OJP_NAME_SPACE, "")
    val stopEventResults: List<StopEventResultDto>? = null,
) : AbstractDeliveryDto()