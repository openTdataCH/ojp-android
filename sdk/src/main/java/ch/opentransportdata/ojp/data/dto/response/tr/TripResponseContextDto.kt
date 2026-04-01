package ch.opentransportdata.ojp.data.dto.response.tr

import ch.opentransportdata.ojp.data.dto.OJP_NAME_SPACE
import ch.opentransportdata.ojp.data.dto.response.PlacesDto
import ch.opentransportdata.ojp.data.dto.response.tr.situations.SituationDto
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

/**
 * Created by Michael Ruppen on 28.06.2024
 */

@Serializable
@XmlSerialName("TripResponseContext", OJP_NAME_SPACE, "")
data class TripResponseContextDto(
    @XmlElement(true)
    @XmlSerialName("Situations", OJP_NAME_SPACE, "")
    val situation: SituationDto? = null,

    @XmlElement(true)
    @XmlSerialName("Places", OJP_NAME_SPACE, "")
    val places: PlacesDto? = null
)