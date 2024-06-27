package ch.opentransportdata.ojp.data.dto.response

import android.os.Parcelable
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

/**
 * Created by Michael Ruppen on 08.04.2024
 */
@Serializable
@Parcelize
@Xml(name = "PlaceResult")
data class PlaceResultDto(
    @Element(name = "Place")
    val place: PlaceDto,
    @PropertyElement(name = "Complete")
    val complete: Boolean,
    @PropertyElement(name = "Probability")
    val probability: Double?,
) : Parcelable {

    var distance: Double = Double.MAX_VALUE
}