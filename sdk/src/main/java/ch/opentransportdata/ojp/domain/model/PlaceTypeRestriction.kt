package ch.opentransportdata.ojp.domain.model

import kotlinx.serialization.SerialName

/**
 * Created by Michael Ruppen on 26.04.2024
 */
enum class PlaceTypeRestriction {
    @SerialName("stop")
    STOP,
    @SerialName("address")
    ADDRESS,
    @SerialName("poi")
    POI,
    @SerialName("coord")
    COORDINATE,
    @SerialName("location")
    LOCATION,
    @SerialName("topographicPlace")
    TOPOGRAPHIC_PLACE,
    @SerialName("unknown")
    UNKNOWN
}