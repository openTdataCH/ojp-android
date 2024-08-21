package ch.opentransportdata.ojp.utils

/**
 * Created by Michael Ruppen on 21.08.2024
 */

fun String.languageCode(): String {
    return when (this) {
        "en" -> "en"
        "de" -> "de"
        "fr" -> "fr"
        "it" -> "it"
        else -> "de"
    }
}