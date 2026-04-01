package ch.opentransportdata.ojp.data.dto.request.tr

import ch.opentransportdata.ojp.data.dto.OJP_NAME_SPACE
import ch.opentransportdata.ojp.data.dto.converter.DurationSerializer
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName
import java.time.Duration

/**
 * Created by Nico Brandenberger on 20.02.2026
 */
@Serializable
@XmlSerialName("IndividualTransportOption", OJP_NAME_SPACE, "")
data class IndividualTransportOptionDto(
    @XmlElement(true)
    @XmlSerialName("ItModeAndModeOfOperation", OJP_NAME_SPACE, "")
    val itModeAndModeOfOperation: ItModeAndModeOfOperationDto? = null,

    @XmlElement(true)
    @XmlSerialName("MinDistance", OJP_NAME_SPACE, "")
    val minDistance: Int? = null,

    @XmlElement(true)
    @XmlSerialName("MaxDistance", OJP_NAME_SPACE, "")
    val maxDistance: Int? = null,

    @XmlElement(true)
    @XmlSerialName("MinDuration", OJP_NAME_SPACE, "")
    @Serializable(with = DurationSerializer::class)
    val minDuration: Duration? = null,

    @XmlElement(true)
    @XmlSerialName("MaxDuration", OJP_NAME_SPACE, "")
    @Serializable(with = DurationSerializer::class)
    val maxDuration: Duration? = null,
)
