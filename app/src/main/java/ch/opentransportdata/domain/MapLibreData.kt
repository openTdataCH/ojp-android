package ch.opentransportdata.domain

import ch.opentransportdata.ojp.data.dto.response.GeoPositionDto

/**
 * Created by Nico Brandenberger on 05.11.2025
 */

data class MapLibreData(
    val id: String,
    val positions: List<GeoPositionDto>,
)