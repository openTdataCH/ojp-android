package ch.opentransportdata.ojp.data.dto.request.tir

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement

/**
 * Created by Michael Ruppen on 07.06.2024
 */
internal data class TripRequestDto(
    @PropertyElement(name = "siri:RequestTimestamp")
    val requestTimestamp: String,

    @Element(name = "Origin")
    val origin: PlaceContextDto,

    @Element(name = "Destination")
    val destination: PlaceContextDto,

    @Element(name = "Via")
    val via: List<TripVia> = emptyList(),

    @Element(name = "TripParams")
    val params: TripParamsDto?

)