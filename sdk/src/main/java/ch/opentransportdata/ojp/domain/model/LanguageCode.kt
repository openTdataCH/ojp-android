package ch.opentransportdata.ojp.domain.model

/**
 * Created by Michael Ruppen on 21.08.2024
 */
enum class LanguageCode {
    DE,
    EN,
    IT,
    FR
}

val LanguageCode.shortName: String
    get() = when (this) {
        LanguageCode.EN -> "en"
        LanguageCode.DE -> "de"
        LanguageCode.FR -> "fr"
        LanguageCode.IT -> "it"
    }
