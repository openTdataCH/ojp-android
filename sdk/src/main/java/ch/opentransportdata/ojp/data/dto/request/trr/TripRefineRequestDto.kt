package ch.opentransportdata.ojp.data.dto.request.trr

import ch.opentransportdata.ojp.data.dto.OJP_NAME_SPACE
import ch.opentransportdata.ojp.data.dto.SIRI_NAME_SPACE
import ch.opentransportdata.ojp.data.dto.SIRI_PREFIX
import ch.opentransportdata.ojp.data.dto.response.tir.TripResultDto
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName
import java.time.LocalDateTime

/**
 * Created by Nico Brandenberger on 07.04.2025
 */

@Serializable
@XmlSerialName("OJPTripRefineRequest", OJP_NAME_SPACE, "")
internal data class TripRefineRequestDto(

    @XmlElement(true)
    @XmlSerialName("RequestTimestamp", SIRI_NAME_SPACE, SIRI_PREFIX)
    @Contextual
    val requestTimestamp: LocalDateTime,

    @XmlElement(true)
    @XmlSerialName("RefineParams", OJP_NAME_SPACE, "")
    val params: TripRefineParamDto? = null,

    @XmlElement(true)
    @XmlSerialName("TripResult", OJP_NAME_SPACE, "")
    val result: TripResultDto,
)

