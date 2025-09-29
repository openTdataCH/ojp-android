package ch.opentransportdata.ojp.data.dto.request.lir

import ch.opentransportdata.ojp.data.dto.SIRI_NAME_SPACE
import ch.opentransportdata.ojp.data.dto.SIRI_PREFIX
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

/**
 * Created by Michael Ruppen on 08.04.2024
 */
@Serializable
internal data class PointDto(
    @XmlElement(true)
    @XmlSerialName("Longitude", SIRI_NAME_SPACE, SIRI_PREFIX)
    val longitude: Double,

    @XmlElement(true)
    @XmlSerialName("Latitude", SIRI_NAME_SPACE, SIRI_PREFIX)
    val latitude: Double
)