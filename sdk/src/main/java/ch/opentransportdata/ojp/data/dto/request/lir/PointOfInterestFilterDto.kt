package ch.opentransportdata.ojp.data.dto.request.lir

import ch.opentransportdata.ojp.data.dto.OJP_NAME_SPACE
import ch.opentransportdata.ojp.data.dto.response.place.PointOfInterestCategoryDto
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

/**
 * Created by Deniz Kalem on 02.07.2026
 *
 */
@Serializable
@XmlSerialName("PointOfInterestFilter", OJP_NAME_SPACE, "")
internal data class PointOfInterestFilterDto(
    @XmlElement(true)
    @XmlSerialName("PointOfInterestCategory", OJP_NAME_SPACE, "")
    val pointOfInterestCategory: List<PointOfInterestCategoryDto>? = null,

    @XmlElement(true)
    @XmlSerialName("Exclude", OJP_NAME_SPACE, "")
    val exclude: Boolean? = null
)