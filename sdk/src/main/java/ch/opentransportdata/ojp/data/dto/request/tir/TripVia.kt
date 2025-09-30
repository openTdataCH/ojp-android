package ch.opentransportdata.ojp.data.dto.request.tir

import ch.opentransportdata.ojp.data.dto.OJP_NAME_SPACE
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

/**
 * Created by Michael Ruppen on 07.06.2024
 */

@Serializable
internal data class TripVia(
    @XmlElement(true)
    @XmlSerialName("ViaPoint", OJP_NAME_SPACE, "")
    val viaPoint: PlaceReferenceDto
)
