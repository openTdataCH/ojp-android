package ch.opentransportdata.presentation.navigation

import ch.opentransportdata.ojp.data.dto.response.PlaceResultDto
import kotlinx.serialization.Serializable

/**
 * Created by Deniz Kalem on 20.05.2026
 */
@Serializable
object StopEventSearchMask

@Serializable
data class StopEventResults(
    val stop: PlaceResultDto
)