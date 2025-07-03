package ch.opentransportdata.ojp.data.dto.converter

import ch.opentransportdata.ojp.domain.model.OccupancyLevel
import com.tickaroo.tikxml.TypeConverter


/**
 * Created by Deniz Kalem on 02.07.2025
 */
internal class OccupancyLevelConverter : TypeConverter<OccupancyLevel> {

    override fun read(ojpValue: String): OccupancyLevel {
        return when (ojpValue) {
            "unknown" -> OccupancyLevel.UNKNOWN
            "empty" -> OccupancyLevel.UNKNOWN
            "undefined" -> OccupancyLevel.UNDEFINED
            "manySeatsAvailable" -> OccupancyLevel.MANY_SEATS_AVAILABLE
            "fewSeatsAvailable" -> OccupancyLevel.FEW_SEATS_AVAILABLE
            "standingRoomOnly" -> OccupancyLevel.STANDING_ROOM_ONLY
            "crushedStandingRoomOnly" -> OccupancyLevel.CRUSHED_STANDING_ROOM_ONLY
            "notAcceptingPassengers" -> OccupancyLevel.NOT_ACCEPTING_PASSENGERS
            "full" -> OccupancyLevel.FULL
            else -> OccupancyLevel.UNKNOWN
        }
    }

    override fun write(type: OccupancyLevel): String {
        return when (type) {
            OccupancyLevel.UNKNOWN -> "unknown"
            OccupancyLevel.EMPTY -> "empty"
            OccupancyLevel.UNDEFINED -> "undefined"
            OccupancyLevel.MANY_SEATS_AVAILABLE -> "manySeatsAvailable"
            OccupancyLevel.FEW_SEATS_AVAILABLE -> "fewSeatsAvailable"
            OccupancyLevel.STANDING_ROOM_ONLY -> "standingRoomOnly"
            OccupancyLevel.CRUSHED_STANDING_ROOM_ONLY -> "crushedStandingRoomOnly"
            OccupancyLevel.NOT_ACCEPTING_PASSENGERS -> "notAcceptingPassengers"
            OccupancyLevel.FULL -> "full"
        }
    }
}