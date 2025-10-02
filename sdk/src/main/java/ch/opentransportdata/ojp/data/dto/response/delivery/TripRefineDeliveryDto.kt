package ch.opentransportdata.ojp.data.dto.response.delivery

import ch.opentransportdata.ojp.data.dto.OJP_NAME_SPACE
import ch.opentransportdata.ojp.data.dto.SIRI_NAME_SPACE
import ch.opentransportdata.ojp.data.dto.SIRI_PREFIX
import ch.opentransportdata.ojp.data.dto.response.tir.TripResponseContextDto
import ch.opentransportdata.ojp.data.dto.response.tir.TripResultDto
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName
import java.time.LocalDateTime

/**
 * Created by Nico Brandenberger on 03.04.2025
 */


@Serializable
@XmlSerialName("OJPTripRefineDelivery", OJP_NAME_SPACE, "")
data class TripRefineDeliveryDto(
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
    @XmlSerialName("CalcTime", OJP_NAME_SPACE, "")
    val calcTime: Int? = null,

    @XmlElement(true)
    @XmlSerialName("TripResult", OJP_NAME_SPACE, "")
    val tripResults: List<TripResultDto>? = null,

    @XmlElement(true)
    @XmlSerialName("TripResponseContext", OJP_NAME_SPACE, "")
    val responseContext: TripResponseContextDto? = null,

    @XmlElement(true)
    @XmlSerialName("LegObjectIdType", OJP_NAME_SPACE, "")
    val unknownLegRef: String? = null,
) : AbstractDeliveryDto()
