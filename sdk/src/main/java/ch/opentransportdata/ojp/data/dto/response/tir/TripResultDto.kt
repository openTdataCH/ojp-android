package ch.opentransportdata.ojp.data.dto.response.tir

import ch.opentransportdata.ojp.data.dto.response.tir.trips.AbstractTripDto
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

/**
 * Created by Michael Ruppen on 28.06.2024
 */
@Xml(name = "TripResult")
data class TripResultDto(
    @PropertyElement(name = "Id")
    val id: String,
    @Element
    val trip: AbstractTripDto,
//    @Element(name = "TripFare")
//    val tripFares: List<TripFare>, //not yet implemented on backend
    @PropertyElement(name = "IsAlternativeOption")
    val isAlternativeOption: Boolean?
)
