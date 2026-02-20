package ch.opentransportdata.ojp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by Michael Ruppen on 31.07.2024
 */
@Parcelize
data class ModeAndModeOfOperationFilter(
    val ptMode: List<PtMode>,
    val exclude: Boolean,
    val railSubmode: String? = null,
    val busSubmode: String? = null,
    val coachSubmode: String? = null,
    val metroSubmode: String? = null,
    val tramSubmode: String? = null,
    val trolleyBusSubmode: String? = null,
    val telecabinSubmode: String? = null,
    val funicularSubmode: String? = null,
    val waterSubmode: String? = null,
    val airSubmode: String? = null,
    val taxiSubmode: String? = null,
    val selfDriveSubmode: String? = null
) : Parcelable