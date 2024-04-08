package ch.opentransportdata.ojp.data.dto.request.lir

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Xml

/**
 * Created by Michael Ruppen on 08.04.2024
 */
@Xml(name = "GeoRestriction")
data class GeoRestrictionDto(
    @Element(name = "Rectangle")
    val rectangle: RectangleDto
)
