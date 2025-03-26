package ch.opentransportdata.ojp.data.dto.response

import android.os.Parcelable
import ch.opentransportdata.ojp.data.dto.response.place.StopPointDto
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
) : Parcelable {

    val stopPoints: List<StopPointDto>
        get() = places.orEmpty().mapNotNull { it.placeType as? StopPointDto }

    fun findParentStation(stopPointRef: String): String {
        return stopPoints.firstOrNull { it.stopPointRef == stopPointRef }?.parentRef ?: stopPointRef
    }
}