package ch.opentransportdata.ojp.data.dto.request.tir

import ch.opentransportdata.ojp.data.dto.response.GeoPositionDto
import ch.opentransportdata.ojp.data.dto.response.NameDto
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.TextContent
import com.tickaroo.tikxml.annotation.Xml

/**
 * Created by Michael Ruppen on 07.06.2024
 */
internal sealed class AbstractPlaceReference(
    open val stationName: NameDto
) {
    //todo: test if parsing works like this
    @Xml(name = "StopPlaceRef")
    data class StopPlaceReference(
        @TextContent
        val ref: String,
        @Element(name = "Name")
        override val stationName: NameDto
    ) : AbstractPlaceReference(stationName)

    @Xml(name = "PlaceRef")
    data class LocationReference(
        @Element(name = "siri:LocationStructure")
        val location: GeoPositionDto,
        @Element(name = "Name")
        override val stationName: NameDto
    ) : AbstractPlaceReference(stationName)
}