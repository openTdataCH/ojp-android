package ch.opentransportdata.ojp.data.dto.response

import android.os.Parcelable
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Xml
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

/**
 * Created by Deniz Kalem on 26.03.2025
 */
@Serializable
@Parcelize
@Xml(name = "Places")
data class PlacesDto(
    @Element(name = "Place")
    val places: List<PlaceDto>? = emptyList()
) : Parcelable