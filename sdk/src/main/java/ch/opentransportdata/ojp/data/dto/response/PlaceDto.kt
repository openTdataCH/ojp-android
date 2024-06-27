package ch.opentransportdata.ojp.data.dto.response

import android.os.Parcelable
import ch.opentransportdata.ojp.data.dto.response.place.AbstractPlaceDto
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

/**
 * Created by Michael Ruppen on 08.04.2024
 */
@Serializable
@Parcelize
data class PlaceDto(
    val placeType: AbstractPlaceDto?,
    val name: NameDto,
    val position: GeoPositionDto,
    val mode: List<ModeDto>? = emptyList(),
): Parcelable
