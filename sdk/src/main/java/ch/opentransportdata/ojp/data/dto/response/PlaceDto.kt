package ch.opentransportdata.ojp.data.dto.response

import ch.opentransportdata.ojp.data.dto.response.place.AbstractPlaceDto

/**
 * Created by Michael Ruppen on 08.04.2024
 */
data class PlaceDto(
    val placeType: AbstractPlaceDto?,
    val name: NameDto,
    val position: GeoPositionDto,
    val mode: List<ModeDto>? = emptyList(),
)
