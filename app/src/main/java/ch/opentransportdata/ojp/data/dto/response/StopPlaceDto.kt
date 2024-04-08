package ch.opentransportdata.ojp.data.dto.response

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

/**
 * Created by Michael Ruppen on 08.04.2024
 */
@Xml(name = "StopPlace")
data class StopPlaceDto(
    @PropertyElement(name = "StopPlaceRef")
    val stopPlaceRef: String,
    @Element(name = "StopPlaceName")
    val name: NameDto?,
    @PropertyElement(name = "TopographicPlaceRef")
    val topographicPlaceRef: String?,
)
