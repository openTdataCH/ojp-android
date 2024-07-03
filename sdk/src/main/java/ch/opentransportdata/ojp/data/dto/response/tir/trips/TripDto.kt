package ch.opentransportdata.ojp.data.dto.response.tir.trips

import ch.opentransportdata.ojp.data.dto.response.tir.LegDto
import ch.opentransportdata.ojp.data.dto.response.tir.leg.TimedLegDto
import ch.opentransportdata.ojp.data.dto.response.tir.leg.TransferLegDto
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
    val transfers: Int,
    @Element(name = "Leg")
    val legs: List<LegDto>,
) : AbstractTripDto() {

    val startWithTransferLeg: Boolean
        get() = legs.first().legType is TransferLegDto

    val endWithTransferLeg: Boolean
        get() = legs.first().legType is TransferLegDto

    val firstTimedLeg: TimedLegDto
        get() = legs.first { it.legType is TimedLegDto }.legType as TimedLegDto

    val lastTimedLeg: TimedLegDto
        get() = legs.last { it.legType is TimedLegDto }.legType as TimedLegDto
}
