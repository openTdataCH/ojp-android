package ch.opentransportdata.ojp.data.dto.converter

import ch.opentransportdata.ojp.domain.model.OccupancyLevel
import com.tickaroo.tikxml.TypeConverter


/**
 * Created by Deniz Kalem on 13.08.2024
 */
internal class OccupancyLevelConverter : TypeConverter<OccupancyLevel> {

    override fun read(ojpValue: String): OccupancyLevel {
        return when (ojpValue) {
            "unknown" -> OccupancyLevel.UNKNOWN
            "manySeatsAvailable" -> OccupancyLevel.MANY_SEATS_AVAILABLE
            "fewSeatsAvailable" -> OccupancyLevel.FEW_SEATS_AVAILABLE
            "standingRoomOnly" -> OccupancyLevel.STANDING_ROOM_ONLY
            "full" -> OccupancyLevel.FULL
            else -> OccupancyLevel.UNKNOWN
        }
    }

    override fun write(type: OccupancyLevel): String {
        return when (type) {
            OccupancyLevel.UNKNOWN -> "unknown"
            OccupancyLevel.MANY_SEATS_AVAILABLE -> "manySeatsAvailable"
            OccupancyLevel.FEW_SEATS_AVAILABLE -> "fewSeatsAvailable"
            OccupancyLevel.STANDING_ROOM_ONLY -> "standingRoomOnly"
            OccupancyLevel.FULL -> "full"
        }
    }
}