package ch.opentransportdata.ojp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by Deniz Kalem on 20.05.2026
 */
@Parcelize
data class StopEventParam(
    val modeFilter: ModeFilter? = null,
    val lineFilter: LineFilter? = null,
    val operatorFilter: OperatorFilter? = null,
    val numberOfResults: Int? = null,
    val stopEventType: StopEventType? = null,
    val includePreviousCalls: Boolean? = null,
    val includeOnwardCalls: Boolean? = null,
    val includeOperatingDays: Boolean? = null,
    val useRealtimeData: RealtimeData? = null,
) : Parcelable