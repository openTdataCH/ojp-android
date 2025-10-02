package ch.opentransportdata.ojp.data.dto.request.tir

import ch.opentransportdata.ojp.data.dto.OJP_NAME_SPACE
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName
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
    @Contextual
    val departureArrivalTime: LocalDateTime? = null
)
