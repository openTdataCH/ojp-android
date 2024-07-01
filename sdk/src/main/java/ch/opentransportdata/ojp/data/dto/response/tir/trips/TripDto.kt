package ch.opentransportdata.ojp.data.dto.response.tir.trips

import ch.opentransportdata.ojp.data.dto.response.tir.LegDto
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

/**
 * Created by Michael Ruppen on 28.06.2024
 */
//todo: check nullability
@Xml(name = "Trip")
data class TripDto(
    @PropertyElement(name = "Id")
    override val id: String,
    @PropertyElement(name = "Duration")
    val duration: String,
    @PropertyElement(name = "StartTime")
    val startTime: String,
    @PropertyElement(name = "EndTime")
    val endTime: String,
    @PropertyElement(name = "Transfers")
    val transfers: String,
    @Element(name = "Leg")
    val legs: List<LegDto>,
) : AbstractTripDto()
