package ch.opentransportdata.ojp.data.dto.request.tir

import ch.opentransportdata.ojp.data.dto.OJP_NAME_SPACE
import ch.opentransportdata.ojp.data.dto.converter.LocalDateTimeSerializer
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

/**
 * Created by Michael Ruppen on 07.06.2024
 */
@Serializable
internal data class PlaceContextDto(
    @XmlElement(true)
    @XmlSerialName("PlaceRef", OJP_NAME_SPACE, "")
    val placeReference: PlaceReferenceDto,

    @XmlElement(true)
    @XmlSerialName("DepArrTime", OJP_NAME_SPACE, "")
    @Serializable(with = LocalDateTimeSerializer::class)
    val departureArrivalTime: LocalDateTime? = null
)
