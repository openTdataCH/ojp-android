package ch.opentransportdata.ojp.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by Deniz Kalem on 02.07.2026
 *
 */
@Serializable
enum class PersonalMode {
    @SerialName("foot")
    FOOT,

    @SerialName("bicycle")
    BICYCLE,

    @SerialName("car")
    CAR,

    @SerialName("motorcycle")
    MOTORCYCLE,

    @SerialName("truck")
    TRUCK,

    @SerialName("scooter")
    SCOOTER,

    @SerialName("other")
    OTHER
}