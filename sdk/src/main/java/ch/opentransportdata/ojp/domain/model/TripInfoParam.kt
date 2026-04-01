package ch.opentransportdata.ojp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by Deniz Kalem on 25.03.2026
 */
@Parcelize
data class TripInfoParam(
    val useRealtimeData: RealtimeData? = null,
    val includeCalls: Boolean = false,
    val includePosition: Boolean = false,
    val includeService: Boolean = false,
    val includeTrackSections: Boolean = false,
    val includeTrackProjection: Boolean = false,
    val includePlacesContext: Boolean = false,
    val includeFormation: Boolean = false,
    val includeSituationsContext: Boolean = false,
) : Parcelable
