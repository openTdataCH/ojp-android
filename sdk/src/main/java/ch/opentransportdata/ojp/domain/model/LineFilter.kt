package ch.opentransportdata.ojp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by Deniz Kalem on 20.05.2026
 */
@Parcelize
data class LineFilter(
    val line: List<Line>? = null,
    val exclude: Boolean? = null,
) : Parcelable