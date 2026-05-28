package ch.opentransportdata.ojp.data.dto.response.ser

import ch.opentransportdata.ojp.data.dto.OJP_NAME_SPACE
import ch.opentransportdata.ojp.data.dto.response.PlacesDto
import ch.opentransportdata.ojp.data.dto.response.tr.situations.SituationDto
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

/**
 * Created by Deniz Kalem on 20.05.2026
 */
@Serializable
@XmlSerialName("StopEventResponseContext", OJP_NAME_SPACE, "")
data class StopEventResponseContextDto(
    @XmlElement(true)
    @XmlSerialName("Situations", OJP_NAME_SPACE, "")
    val situation: SituationDto? = null,

    @XmlElement(true)
    @XmlSerialName("Places", OJP_NAME_SPACE, "")
    val places: PlacesDto? = null,
)