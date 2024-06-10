package ch.opentransportdata.ojp.data.dto.request.tir

import ch.opentransportdata.ojp.data.dto.response.NameDto

/**
 * Created by Michael Ruppen on 07.06.2024
 */
internal data class PlaceReferenceDto(
    val placeRef: AbstractPlaceReference,
    val stationName: NameDto
)
