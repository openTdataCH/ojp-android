package ch.opentransportdata.ojp.data.dto.response.tir.leg

import ch.opentransportdata.ojp.data.dto.response.GeoPositionDto
import ch.opentransportdata.ojp.data.dto.response.NameDto
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

/**
 * Created by Michael Ruppen on 28.06.2024
 */
@Xml
data class LegStartEndDto(
    @PropertyElement(name = "siri:StopPointRef")
    val stopPointRef: String?,
    @Element(name = "Name")
    val name: NameDto?,
    @Element(name = "GeoPosition")
    val geoPosition: GeoPositionDto?
)
