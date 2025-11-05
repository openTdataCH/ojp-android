package ch.opentransportdata.presentation.navigation

import ch.opentransportdata.ojp.data.dto.response.GeoPositionDto
import ch.opentransportdata.ojp.data.dto.response.PlaceResultDto
import kotlinx.serialization.Serializable

/**
 * Created by Michael Ruppen on 24.06.2024
 */
@Serializable
object TripSearchMask

@Serializable
data class TripResults(
    val origin: PlaceResultDto,
    val via: PlaceResultDto? = null,
    val destination: PlaceResultDto
)

@Serializable
data class TripMap(
    val coordinates: List<GeoPositionDto>,
    val zoom: Double,
)
