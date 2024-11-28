package ch.opentransportdata.ojp.data.dto.converter

import ch.opentransportdata.ojp.domain.model.ScopeType
import com.tickaroo.tikxml.TypeConverter

/**
 * Created by Deniz Kalem on 27.11.2024
 */
internal class ScopeTypeConverter : TypeConverter<ScopeType> {

    override fun read(value: String): ScopeType {
        return when (value) {
            "unknown" -> ScopeType.UNKNOWN
            "stopPlace" -> ScopeType.STOP_PLACE
            "line" -> ScopeType.LINE
            "route" -> ScopeType.ROUTE
            "publicTransportService" -> ScopeType.PUBLIC_TRANSPORT_SERVICE
            "operator" -> ScopeType.OPERATOR
            "city" -> ScopeType.CITY
            "area" -> ScopeType.AREA
            "stopPoint" -> ScopeType.STOP_POINT
            "stopPlaceComponent" -> ScopeType.STOP_PLACE_COMPONENT
            "place" -> ScopeType.PLACE
            "network" -> ScopeType.NETWORK
            "vehicleJourney" -> ScopeType.VEHICLE_JOURNEY
            "datedVehicleJourney" -> ScopeType.DATED_VEHICLE_JOURNEY
            "connectionLink" -> ScopeType.CONNECTION_LINK
            "interchange" -> ScopeType.INTERCHANGE
            "allPT" -> ScopeType.ALL_PT
            "general" -> ScopeType.GENERAL
            "road" -> ScopeType.ROAD
            "undefined" -> ScopeType.UNDEFINED
            else -> ScopeType.UNKNOWN
        }
    }

    override fun write(value: ScopeType): String {
        return when (value) {
            ScopeType.UNKNOWN -> "unknown"
            ScopeType.STOP_PLACE -> "stopPlace"
            ScopeType.LINE -> "line"
            ScopeType.ROUTE -> "route"
            ScopeType.PUBLIC_TRANSPORT_SERVICE -> "publicTransportService"
            ScopeType.OPERATOR -> "operator"
            ScopeType.CITY -> "city"
            ScopeType.AREA -> "area"
            ScopeType.STOP_POINT -> "stopPoint"
            ScopeType.STOP_PLACE_COMPONENT -> "stopPlaceComponent"
            ScopeType.PLACE -> "place"
            ScopeType.NETWORK -> "network"
            ScopeType.VEHICLE_JOURNEY -> "vehicleJourney"
            ScopeType.DATED_VEHICLE_JOURNEY -> "datedVehicleJourney"
            ScopeType.CONNECTION_LINK -> "connectionLink"
            ScopeType.INTERCHANGE -> "interchange"
            ScopeType.ALL_PT -> "allPT"
            ScopeType.GENERAL -> "general"
            ScopeType.ROAD -> "road"
            ScopeType.UNDEFINED -> "undefined"
        }
    }
}