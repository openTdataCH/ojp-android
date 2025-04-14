package ch.opentransportdata.ojp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by Nico Brandenberger on 04.04.2025
 */

@Parcelize
data class TripRefineParam(
    val includeTrackSections: Boolean = false,
    val includeLegProjection: Boolean = false,
    val includeTurnDescription: Boolean = false,
    val includeIntermediateStops: Boolean = false,
    val includeAllRestrictedLines: Boolean = false,
    val useRealtimeData: RealtimeData? = null,
) : Parcelable
