package ch.opentransportdata.ojp.data.dto.response

import android.os.Parcelable
import ch.opentransportdata.ojp.data.dto.response.place.AbstractPlaceDto
import ch.opentransportdata.ojp.data.dto.response.place.AddressDto
import ch.opentransportdata.ojp.data.dto.response.place.PointOfInterestDto
import ch.opentransportdata.ojp.data.dto.response.place.StopPlaceDto
import ch.opentransportdata.ojp.data.dto.response.place.StopPointDto
import ch.opentransportdata.ojp.data.dto.response.place.TopographicPlaceDto
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Xml
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

/**
 * Created by Michael Ruppen on 08.04.2024
 *
 * Serializable and Parcelize annotation is both needed for compose navigation with custom types
 */
@Serializable
@Parcelize
@Xml(name = "Place")
data class PlaceDto(
    @Element(name = "StopPoint")
    val stopPoint: StopPointDto? = null,
    @Element(name = "StopPlace")
    val stopPlace: StopPlaceDto? = null,
    @Element(name = "TopographicPlace")
    val topographicPlace: TopographicPlaceDto? = null,
    @Element(name = "PointOfInterest")
    val pointOfInterest: PointOfInterestDto? = null,
    @Element(name = "Address")
    val address: AddressDto? = null,
    @Element(name = "Name")
    val name: NameDto?,
    @Element(name = "GeoPosition")
    val position: GeoPositionDto,
    @Element(name = "Mode")
    val mode: List<ModeDto>? = emptyList(),
) : Parcelable {

    val placeType: AbstractPlaceDto?
        get() = listOfNotNull(stopPoint, stopPlace, topographicPlace, pointOfInterest, address).singleOrNull()
}