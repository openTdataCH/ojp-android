package ch.opentransportdata.ojp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.Duration

/**
 * Created by Deniz Kalem on 20.05.2026
 */
@Parcelize
data class StopEventParam(
    val modeFilter: ModeFilter? = null,
    val lineFilter: LineFilter? = null,
    val operatorFilter: OperatorFilter? = null,
    val includeAllRestrictedLines: Boolean? = null,
    val numberOfResults: Int? = null,
    val timeWindow: Duration? = null,
    val stopEventType: StopEventType? = null,
    val includePreviousCalls: Boolean? = null,
    val includeOnwardCalls: Boolean? = null,
    val includeOperatingDays: Boolean? = null,
    val useRealtimeData: RealtimeData? = null,
    val includePlacesContext: Boolean? = null,
    val includeSituationsContext: Boolean? = null,
    val includeStopHierarchy: StopHierarchy? = null,
) : Parcelable