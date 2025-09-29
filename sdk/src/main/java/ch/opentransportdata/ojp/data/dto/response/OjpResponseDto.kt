package ch.opentransportdata.ojp.data.dto.response

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
@XmlSerialName("OJPResponse", OJP_NAME_SPACE, "")
internal data class OjpResponseDto(
    // e.g. <siri:ServiceDelivery>...</siri:ServiceDelivery>
    @XmlElement(true)
    @XmlSerialName("ServiceDelivery", SIRI_NAME_SPACE, SIRI_PREFIX)
    val serviceDelivery: ServiceDeliveryDto? = null
)