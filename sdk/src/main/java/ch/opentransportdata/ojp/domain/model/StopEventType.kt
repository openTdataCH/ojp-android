package ch.opentransportdata.ojp.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by Deniz Kalem on 20.05.2026
 */
@Serializable
enum class StopEventType {
    @SerialName("arrival")
    ARRIVAL,

    @SerialName("departure")
    DEPARTURE,

    @SerialName("both")
    BOTH
}