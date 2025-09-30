package ch.opentransportdata.ojp.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by Michael Ruppen on 23.04.2024
 */
@Serializable
enum class PtMode {
    @SerialName("unknown")
    UNKNOWN,
    @SerialName("rail")
    RAIL,
    @SerialName("coach")
    COACH,
    @SerialName("suburbanRail")
    SUBURBAN_RAIL,
    @SerialName("urbanRail")
    URBAN_RAIL,
    @SerialName("metro")
    METRO,
    @SerialName("underground")
    UNDERGROUND,
    @SerialName("bus")
    BUS,
    @SerialName("trolleyBus")
    TROLLEY_BUS,
    @SerialName("tram")
    TRAM,
    @SerialName("water")
    WATER,
    @SerialName("air")
    AIR,
    @SerialName("telecabin")
    TELECABIN,
    @SerialName("funicular")
    FUNICULAR,
    @SerialName("taxi")
    TAXI,
    @SerialName("selfDrive")
    SELF_DRIVE,
    @SerialName("all")
    ALL
}