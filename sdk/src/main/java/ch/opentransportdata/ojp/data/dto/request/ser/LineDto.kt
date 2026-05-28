package ch.opentransportdata.ojp.data.dto.request.ser

import ch.opentransportdata.ojp.data.dto.OJP_NAME_SPACE
import ch.opentransportdata.ojp.data.dto.SIRI_NAME_SPACE
import ch.opentransportdata.ojp.data.dto.SIRI_PREFIX
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

/**
 * Created by Deniz Kalem on 20.05.2026
 */
@Serializable
@XmlSerialName("Line", OJP_NAME_SPACE, "")
internal data class LineDto(

    @XmlElement(true)
    @XmlSerialName("LineRef", SIRI_NAME_SPACE, SIRI_PREFIX)
    val lineRef: String,

    @XmlElement(true)
    @XmlSerialName("DirectionRef", SIRI_NAME_SPACE, SIRI_PREFIX)
    val directionRef: String? = null,
)