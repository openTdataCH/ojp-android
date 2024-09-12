package ch.opentransportdata.presentation.utils

import java.time.Duration
import java.util.Locale

/**
 * Created by Deniz Kalem on 12.09.2024
 */
fun Duration.toFormattedString(): String {
    return String.format(Locale.getDefault(), "%dh %dmin", this.toHoursPart(), this.toMinutesPart())
}