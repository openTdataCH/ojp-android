package ch.opentransportdata.ojp.data.dto.response

import android.os.Parcelable
import ch.opentransportdata.ojp.data.dto.OJP_NAME_SPACE
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

/**
 * Created by Michael Ruppen on 08.04.2024
 *
 * Serializable and Parcelize annotation is both needed for compose navigation with custom types
 */
@Parcelize
@Serializable
@XmlSerialName("PlaceResult", OJP_NAME_SPACE, "")
data class PlaceResultDto(
    @XmlElement(true)
    @XmlSerialName("Place", OJP_NAME_SPACE, "")
    val place: PlaceDto? = null,

    @XmlElement(true)
    @XmlSerialName("Complete", OJP_NAME_SPACE, "")
    val complete: Boolean,

    @XmlElement(true)
    @XmlSerialName("Probability", OJP_NAME_SPACE, "")
    val probability: Double? = null
) : Parcelable {
    @IgnoredOnParcel
    @Transient
    var distance: Double = Double.MAX_VALUE
}