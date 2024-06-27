package ch.opentransportdata.ojp.data.dto.request.tir

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Xml

/**
 * Created by Michael Ruppen on 07.06.2024
 */

@Xml(name = "ViaPoint")
internal data class TripVia(
    @Element(name = "PlaceRef")
    val viaPoint: PlaceReferenceDto
)
