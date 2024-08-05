package ch.opentransportdata.ojp.data.dto.response.tir.leg

import ch.opentransportdata.ojp.data.dto.response.GeoPositionDto
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Xml

/**
 * Created by Michael Ruppen on 28.06.2024
 */
@Xml(name = "LinkProjection")
data class LinearShapeDto(
    @Element(name = "Position")
    val positions: List<GeoPositionDto>
)
