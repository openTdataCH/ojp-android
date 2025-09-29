package ch.opentransportdata.ojp.data.dto.request

import ch.opentransportdata.ojp.data.dto.OJP_NAME_SPACE
import ch.opentransportdata.ojp.data.dto.SIRI_NAME_SPACE
import ch.opentransportdata.ojp.data.dto.SIRI_PREFIX
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

/**
 * Created by Michael Ruppen on 08.04.2024
 */
@Serializable
@XmlSerialName("OJPRequest", OJP_NAME_SPACE, "")
internal data class OjpRequestDto(
    @XmlElement(true)
    @XmlSerialName("ServiceRequest", SIRI_NAME_SPACE, SIRI_PREFIX)
    val serviceRequest: ServiceRequestDto? = null
)