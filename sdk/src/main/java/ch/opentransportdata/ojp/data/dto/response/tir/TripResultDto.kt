package ch.opentransportdata.ojp.data.dto.response.tir

import ch.opentransportdata.ojp.data.dto.response.tir.trips.AbstractTripDto
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

/**
 * Created by Michael Ruppen on 28.06.2024
 */
data class TripResultDto(
    val id: String,
    val trip: AbstractTripDto
    //todo: check for more elements
)
