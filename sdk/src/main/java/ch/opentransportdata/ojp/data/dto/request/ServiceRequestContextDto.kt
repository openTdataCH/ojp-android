package ch.opentransportdata.ojp.data.dto.request

import ch.opentransportdata.ojp.data.dto.SIRI_NAME_SPACE
import ch.opentransportdata.ojp.data.dto.SIRI_PREFIX
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

/**
 * Created by Michael Ruppen on 21.08.2024
 */
@Serializable
@XmlSerialName("ServiceRequestContext", SIRI_NAME_SPACE, SIRI_PREFIX)
internal data class ServiceRequestContextDto(
    @XmlElement(true)
    @XmlSerialName("Language", SIRI_NAME_SPACE, SIRI_PREFIX)
    val language: String
)
