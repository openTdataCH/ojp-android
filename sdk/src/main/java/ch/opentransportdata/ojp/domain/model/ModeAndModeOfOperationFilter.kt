package ch.opentransportdata.ojp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by Michael Ruppen on 31.07.2024
 */
@Parcelize
data class ModeAndModeOfOperationFilter(
    val ptMode: List<PtMode>,
    val exclude: Boolean
) : Parcelable