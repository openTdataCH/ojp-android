package ch.opentransportdata.ojp.data.dto.response

import android.os.Parcelable
import ch.opentransportdata.ojp.data.dto.OJP_NAME_SPACE
import ch.opentransportdata.ojp.data.dto.response.place.StopPointDto
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

/**
 * Created by Deniz Kalem on 26.03.2025
 */
@Parcelize
@Serializable
@XmlSerialName("Places", OJP_NAME_SPACE, "")
data class PlacesDto(
    @XmlElement(true)
    @XmlSerialName("Place", OJP_NAME_SPACE, "")
    val places: List<PlaceDto>? = emptyList()
) : Parcelable {

    val stopPoints: List<StopPointDto>
        get() = places.orEmpty().mapNotNull { it.placeType as? StopPointDto }

    fun findParentStation(stopPointRef: String): String {
        return stopPoints.firstOrNull { it.stopPointRef == stopPointRef }?.parentRef ?: stopPointRef
    }
}