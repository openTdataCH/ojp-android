package ch.opentransportdata.ojp.data.dto.response.tir.leg

import android.os.Parcelable
import ch.opentransportdata.ojp.data.dto.OJP_NAME_SPACE
import ch.opentransportdata.ojp.data.dto.response.GeoPositionDto
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

/**
 * Created by Michael Ruppen on 28.06.2024
 */
@Parcelize
@Serializable
@XmlSerialName("LinkProjection", OJP_NAME_SPACE, "")
data class LinearShapeDto(
    @XmlElement(true)
    @XmlSerialName("Position", OJP_NAME_SPACE, "")
    val positions: List<GeoPositionDto>
) : Parcelable