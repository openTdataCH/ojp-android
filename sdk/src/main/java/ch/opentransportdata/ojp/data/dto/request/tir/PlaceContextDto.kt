package ch.opentransportdata.ojp.data.dto.request.tir

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement

/**
 * Created by Michael Ruppen on 07.06.2024
 */
internal data class PlaceContextDto(
    @Element(name = "PlaceRef")
    val placeReference: PlaceReferenceDto,
    @PropertyElement(name = "DepArrTime")
    val departureArrivalTime: String?
)
