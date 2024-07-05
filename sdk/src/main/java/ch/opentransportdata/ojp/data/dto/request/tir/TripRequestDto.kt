package ch.opentransportdata.ojp.data.dto.request.tir

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

/**
 * Created by Michael Ruppen on 07.06.2024
 */
@Xml(name = "OJPTripRequest")
internal data class TripRequestDto(
    @PropertyElement(name = "siri:RequestTimestamp")
    val requestTimestamp: String,

    @Element(name = "Origin")
    val origin: PlaceContextDto,

    @Element(name = "Destination")
    val destination: PlaceContextDto,

    @Element(name = "Via")
    val via: List<TripVia> = emptyList(),

    @Element(name = "Params")
    val params: TripParamsDto?

)