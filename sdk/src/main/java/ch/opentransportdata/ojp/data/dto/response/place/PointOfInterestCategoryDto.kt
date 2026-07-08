package ch.opentransportdata.ojp.data.dto.response.place

import android.os.Parcelable
import ch.opentransportdata.ojp.data.dto.OJP_NAME_SPACE
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

/**
 * Created by Deniz Kalem on 02.07.2026
 *
 */
@Parcelize
@Serializable
@XmlSerialName("PointOfInterestCategory", OJP_NAME_SPACE, "")
data class PointOfInterestCategoryDto(
    @XmlElement(true)
    @XmlSerialName("OsmTag", OJP_NAME_SPACE, "")
    val osmTag: OsmTagDto? = null,

    @XmlElement(true)
    @XmlSerialName("PointOfInterestClassification", OJP_NAME_SPACE, "")
    val pointOfInterestClassification: String? = null
) : Parcelable {

    val value: String?
        get() = pointOfInterestClassification ?: osmTag?.value
}
