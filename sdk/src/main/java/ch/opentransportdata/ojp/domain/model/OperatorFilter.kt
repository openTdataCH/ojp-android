package ch.opentransportdata.ojp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by Deniz Kalem on 20.05.2026
 */
@Parcelize
data class OperatorFilter(
    val operatorRef: List<String>? = null,
    val exclude: Boolean? = null,
) : Parcelable