package ch.opentransportdata.ojp.domain.model

import androidx.annotation.IntRange

/**
 * Created by Michael Ruppen on 07.06.2024
 */
data class TripParams(
    @IntRange(from = 0, to = 100)
    val numberOfResults: Int? = null,
    @IntRange(from = 0, to = 100)
    val numberOfResultsBefore: Int? = null,
    @IntRange(from = 0, to = 100)
    val numberOfResultsAfter: Int? = null,
    val includeTrackSections: Boolean = false,
    val includeLegProjection: Boolean = false,
    val includeTurnDescription: Boolean = false,
    val includeIntermediateStops: Boolean = false,
    val includeAllRestrictedLines: Boolean = false,
    val useRealtimeData: RealtimeData? = null,
    val modeAndModeOfOperationFilter: List<ModeAndModeOfOperationFilter>?
)