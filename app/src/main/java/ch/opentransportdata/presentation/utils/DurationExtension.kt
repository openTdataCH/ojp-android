package ch.opentransportdata.presentation.utils

import java.time.Duration

/**
 * Created by Deniz Kalem on 12.09.2024
 */
fun Duration.toFormattedString(): String {
    return when {
        toHoursPart() == 0 -> "${this.toMinutesPart()}min"
        else -> "${this.toHoursPart()}h ${this.toMinutesPart()}min"
    }
}