package ch.opentransportdata.ojp.data.dto.request.tir

import ch.opentransportdata.ojp.data.dto.response.GeoPositionDto
import ch.opentransportdata.ojp.data.dto.response.NameDto
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

/**
 * Created by Michael Ruppen on 07.06.2024
 */
@Xml(name = "PlaceRef")
internal data class PlaceReferenceDto(
    //If more types needed, create custom typeAdapter and parse only necessary
    @PropertyElement(name = "StopPlaceRef")
    val ref: String? = null,
    @Element(name = "StopPlaceName")
    val stationName: NameDto?, 
    @Element(name = "siri:LocationStructure") //todo: check if schema is correct (when working on backend), think of solution where only send this if is Address
    val position: GeoPositionDto? = null
)