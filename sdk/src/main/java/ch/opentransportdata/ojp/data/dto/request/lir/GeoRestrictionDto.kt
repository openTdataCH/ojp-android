package ch.opentransportdata.ojp.data.dto.request.lir

import ch.opentransportdata.ojp.data.dto.OJP_NAME_SPACE
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName
import kotlinx.serialization.Serializable

/**
 * Created by Michael Ruppen on 08.04.2024
 */
@Serializable
@XmlSerialName("GeoRestriction", OJP_NAME_SPACE, "")
internal data class GeoRestrictionDto(
    @XmlElement(true)
    @XmlSerialName("Rectangle", OJP_NAME_SPACE, "")
    val rectangle: RectangleDto?
)