package ch.opentransportdata.ojp.data.dto.converter

import ch.opentransportdata.ojp.domain.model.PtMode
import com.tickaroo.tikxml.TypeConverter


/**
 * Created by Michael Ruppen on 23.04.2024
 */
internal class PtModeTypeConverter : TypeConverter<PtMode> {

    override fun read(ojpValue: String): PtMode {
        return when (ojpValue) {
            "rail" -> PtMode.RAIL
            "coach" -> PtMode.COACH
            "suburbanRail" -> PtMode.SUBURBAN_RAIL
            "urbanRail" -> PtMode.URBAN_RAIL
            "metro" -> PtMode.METRO
            "underground" -> PtMode.UNDERGROUND
            "bus" -> PtMode.BUS
            "trolleyBus" -> PtMode.TROLLEY_BUS
            "tram" -> PtMode.TRAM
            "water" -> PtMode.WATER
            "air" -> PtMode.AIR
            "telecabin" -> PtMode.TELECABIN
            "funicular" -> PtMode.FUNICULAR
            "taxi" -> PtMode.TAXI
            "selfDrive" -> PtMode.SELF_DRIVE
            "all" -> PtMode.ALL
            else -> PtMode.UNKNOWN
        }
    }

    override fun write(mode: PtMode): String {
        return when (mode) {
            PtMode.RAIL -> "rail"
            PtMode.COACH -> "coach"
            PtMode.SUBURBAN_RAIL -> "suburbanRail"
            PtMode.URBAN_RAIL -> "urbanRail"
            PtMode.METRO -> "metro"
            PtMode.UNDERGROUND -> "underground"
            PtMode.BUS -> "bus"
            PtMode.TROLLEY_BUS -> "trolleyBus"
            PtMode.TRAM -> "tram"
            PtMode.WATER -> "water"
            PtMode.AIR -> "air"
            PtMode.TELECABIN -> "telecabin"
            PtMode.FUNICULAR -> "funicular"
            PtMode.TAXI -> "taxi"
            PtMode.SELF_DRIVE -> "selfDrive"
            PtMode.ALL -> "all"
            else -> "unknown"
        }
    }
}
