package ch.opentransportdata.ojp.domain.model

/**
 * Created by Deniz Kalem on 25.06.2025
 */
enum class OccupancyLevel {
    UNKNOWN,
    EMPTY,
    UNDEFINED,
    MANY_SEATS_AVAILABLE,
    FEW_SEATS_AVAILABLE,
    STANDING_ROOM_ONLY,
    CRUSHED_STANDING_ROOM_ONLY,
    NOT_ACCEPTING_PASSENGERS,
    FULL
}