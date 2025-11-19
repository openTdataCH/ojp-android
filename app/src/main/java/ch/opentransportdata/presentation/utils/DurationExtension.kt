package ch.opentransportdata.presentation.utils

import java.time.Duration

/**
 * Created by Deniz Kalem on 12.09.2024
 */
fun Duration.toFormattedString(): String {
    val totalMinutes = this.toMinutes()
    val hours = (totalMinutes / 60)
    val minutes = (totalMinutes % 60)
    return when {
        hours <= 0 -> "${minutes}min"
        else -> "${hours}h ${minutes}min"
    }
}