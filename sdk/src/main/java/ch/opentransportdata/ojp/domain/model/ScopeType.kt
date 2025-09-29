package ch.opentransportdata.ojp.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by Deniz Kalem on 27.11.2024
 */
@Serializable
enum class ScopeType {
    @SerialName("unknown") UNKNOWN,
    @SerialName("stopPlace") STOP_PLACE,
    @SerialName("line") LINE,
    @SerialName("route") ROUTE,
    @SerialName("publicTransportService") PUBLIC_TRANSPORT_SERVICE,
    @SerialName("operator") OPERATOR,
    @SerialName("city") CITY,
    @SerialName("area") AREA,
    @SerialName("stopPoint") STOP_POINT,
    @SerialName("stopPlaceComponent") STOP_PLACE_COMPONENT,
    @SerialName("place") PLACE,
    @SerialName("network") NETWORK,
    @SerialName("vehicleJourney") VEHICLE_JOURNEY,
    @SerialName("datedVehicleJourney") DATED_VEHICLE_JOURNEY,
    @SerialName("connectionLink") CONNECTION_LINK,
    @SerialName("interchange") INTERCHANGE,
    @SerialName("allPT") ALL_PT,
    @SerialName("general") GENERAL,
    @SerialName("road") ROAD,
    @SerialName("undefined") UNDEFINED
}