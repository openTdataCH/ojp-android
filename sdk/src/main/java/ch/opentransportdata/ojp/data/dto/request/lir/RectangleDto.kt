package ch.opentransportdata.ojp.data.dto.request.lir

import ch.opentransportdata.ojp.data.dto.OJP_NAME_SPACE
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

/**
 * Created by Michael Ruppen on 08.04.2024
 */
@Serializable
@XmlSerialName("Rectangle", OJP_NAME_SPACE, "")
internal data class RectangleDto(
    @XmlElement(true)
    @XmlSerialName("UpperLeft", OJP_NAME_SPACE, "")
    val upperLeft: PointDto,

    @XmlElement(true)
    @XmlSerialName("LowerRight", OJP_NAME_SPACE, "")
    val lowerRight: PointDto
)
