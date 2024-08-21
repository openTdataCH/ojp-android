package ch.opentransportdata.presentation.utils

import ch.opentransportdata.ojp.domain.model.LanguageCode

/**
 * Created by Michael Ruppen on 21.08.2024
 */
fun String.toOjpLanguageCode(): LanguageCode {
    return when (this) {
        "en" -> LanguageCode.EN
        "de" -> LanguageCode.DE
        "fr" -> LanguageCode.FR
        "it" -> LanguageCode.IT
        else -> LanguageCode.DE
    }
}