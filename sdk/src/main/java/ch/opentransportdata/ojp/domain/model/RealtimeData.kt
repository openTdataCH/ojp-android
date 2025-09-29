package ch.opentransportdata.ojp.domain.model

import kotlinx.serialization.SerialName

/**
 * Created by Deniz Kalem on 13.08.2024
 */
enum class RealtimeData {
    @SerialName("full")
    FULL,

    @SerialName("explanatory")
    EXPLANATORY,

    @SerialName("none")
    NONE,
    @SerialName("unknown")
    UNKNOWN
}