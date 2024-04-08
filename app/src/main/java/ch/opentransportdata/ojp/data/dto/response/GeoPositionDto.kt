package ch.opentransportdata.ojp.data.dto.response

import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

/**
 * Created by Michael Ruppen on 08.04.2024
 */
@Xml(name = "GeoPosition")
data class GeoPositionDto(
    @PropertyElement(name = "siri:Longitude")
    val longitude: Double?,
    @PropertyElement(name = "siri:Latitude")
    val latitude: Double?
)