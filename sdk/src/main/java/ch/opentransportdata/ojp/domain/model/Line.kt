package ch.opentransportdata.ojp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by Deniz Kalem on 20.05.2026
 */
@Parcelize
data class Line(
    val lineRef: String,
    val directionRef: String? = null,
) : Parcelable