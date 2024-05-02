package ch.opentransportdata.ojp.data.dto.converter

import ch.opentransportdata.ojp.domain.model.PlaceTypeRestriction
import com.tickaroo.tikxml.TypeConverter


/**
 * Created by Michael Ruppen on 23.04.2024
 */
internal class PlaceTypeRestrictionConverter : TypeConverter<PlaceTypeRestriction> {

    override fun read(ojpValue: String): PlaceTypeRestriction {
        return when (ojpValue) {
            "stop" -> PlaceTypeRestriction.STOP
            "address" -> PlaceTypeRestriction.ADDRESS
            "poi" -> PlaceTypeRestriction.POI
            "coord" -> PlaceTypeRestriction.COORDINATE
            "location" -> PlaceTypeRestriction.LOCATION
            "topographicPlace" -> PlaceTypeRestriction.TOPOGRAPHIC_PLACE
            else -> PlaceTypeRestriction.UNKNOWN
        }
    }

    override fun write(type: PlaceTypeRestriction): String {
        return when (type) {
            PlaceTypeRestriction.STOP -> "stop"
            PlaceTypeRestriction.ADDRESS -> "address"
            PlaceTypeRestriction.POI -> "poi"
            PlaceTypeRestriction.COORDINATE -> "coord"
            PlaceTypeRestriction.LOCATION -> "location"
            PlaceTypeRestriction.TOPOGRAPHIC_PLACE -> "topographicPlace"
            else -> "unknown"
        }
    }
}
