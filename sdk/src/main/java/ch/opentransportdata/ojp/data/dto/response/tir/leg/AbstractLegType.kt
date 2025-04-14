package ch.opentransportdata.ojp.data.dto.response.tir.leg

import android.os.Parcelable

/**
 * Created by Michael Ruppen on 28.06.2024
 */
abstract class AbstractLegType : Parcelable

fun AbstractLegType.minimalCopy(): AbstractLegType {
    return when (this) {
        is ContinuousLegDto -> this.minimalCopy()
        is TimedLegDto -> this.minimalCopy()
        is TransferLegDto -> this
        else ->  this
    }
}