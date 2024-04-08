package ch.opentransportdata.ojp.data.dto.response

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Xml

/**
 * Created by Michael Ruppen on 08.04.2024
 */
@Xml(name = "Place")
data class PlaceDto(
    @Element(name = "StopPlace")
    val stopPlace: StopPlaceDto?,
    @Element(name = "Name")
    val name: NameDto,
    @Element(name = "GeoPosition")
    val position: GeoPositionDto,
    @Element(name = "Mode")
    val mode: Mode,
)

