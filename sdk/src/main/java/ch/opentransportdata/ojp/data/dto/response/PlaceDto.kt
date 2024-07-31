package ch.opentransportdata.ojp.data.dto.response

import android.os.Parcelable
import ch.opentransportdata.ojp.data.dto.response.place.AbstractPlaceDto
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
    @Element
    val placeType: AbstractPlaceDto?,
    @Element(name = "Name")
    val name: NameDto,
    @Element(name = "GeoPosition")
    val position: GeoPositionDto,
    @Element(name = "Mode")
    val mode: List<ModeDto>? = emptyList(),
) : Parcelable
