package ch.opentransportdata.ojp.data.dto.request.lir

import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

/**
 * Created by Michael Ruppen on 08.04.2024
 */
@Xml(writeNamespaces = ["siri=http://www.siri.org.uk/siri"])
data class PointDto(
    @PropertyElement(name = "siri:Longitude")
    val longitude: String,

    @PropertyElement(name = "siri:Latitude")
    val latitude: String
)