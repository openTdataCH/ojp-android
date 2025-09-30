package ch.opentransportdata.ojp.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by Deniz Kalem on 25.06.2025
 */
@Serializable
enum class OccupancyLevel {
    @SerialName("unknown")
    UNKNOWN,
    @SerialName("empty")
    EMPTY,
    @SerialName("undefined")
    UNDEFINED,
    @SerialName("manySeatsAvailable")
    MANY_SEATS_AVAILABLE,
    @SerialName("fewSeatsAvailable")
    FEW_SEATS_AVAILABLE,
    @SerialName("standingRoomOnly")
    STANDING_ROOM_ONLY,
    @SerialName("crushedStandingRoomOnly")
    CRUSHED_STANDING_ROOM_ONLY,
    @SerialName("notAcceptingPassengers")
    NOT_ACCEPTING_PASSENGERS,
    @SerialName("full")
    FULL
}