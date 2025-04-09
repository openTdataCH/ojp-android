package ch.opentransportdata.ojp.data.dto.response

import android.os.Parcelable
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

/**
 * Created by Michael Ruppen on 08.04.2024
 *
 * Serializable and Parcelize annotation is both needed for compose navigation with custom types
 */
@Serializable
@Parcelize
@Xml(name = "PlaceResult")
data class PlaceResultDto(
    @Element(name = "Place")
    val place: PlaceDto? = null,
    @PropertyElement(name = "Complete")
    val complete: Boolean,
    @PropertyElement(name = "Probability")
    val probability: Double?,
) : Parcelable {

    @IgnoredOnParcel
    var distance: Double = Double.MAX_VALUE
}