package ch.opentransportdata.ojp.data.dto.response.tir.trips

import ch.opentransportdata.ojp.data.dto.response.tir.LegDto
import ch.opentransportdata.ojp.data.dto.response.tir.leg.TimedLegDto
import ch.opentransportdata.ojp.data.dto.response.tir.leg.TransferLegDto
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml
import java.time.LocalDateTime

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
    val startTime: LocalDateTime,
    @PropertyElement(name = "EndTime")
    val endTime: LocalDateTime,
    @PropertyElement(name = "Transfers")
    val transfers: Int,
    @Element(name = "Leg")
    val legs: List<LegDto>,
) : AbstractTripDto() {

    val startWithTransferLeg: Boolean
        get() = legs.first().legType is TransferLegDto

    val endWithTransferLeg: Boolean
        get() = legs.first().legType is TransferLegDto

    val startWithTimedLeg: Boolean
        get() = legs.first().legType is TimedLegDto

    val firstTimedLeg: TimedLegDto
        get() = legs.first { it.legType is TimedLegDto }.legType as TimedLegDto

    val lastTimedLeg: TimedLegDto
        get() = legs.last { it.legType is TimedLegDto }.legType as TimedLegDto

    val departureDelayInMinutes: Long
        get() = if (startWithTimedLeg) firstTimedLeg.legBoard.serviceDeparture.delay.toMinutes() else 0

    val arrivalDelayInMinutes: Long
        get() = if (lastTimedLeg.legAlight.serviceArrival.hasDelay) lastTimedLeg.legAlight.serviceArrival.delay.toMinutes() else 0

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TripDto

        return this.hashCode() == other.hashCode()
    }

    override fun hashCode(): Int {
        var result = transfers
        legs.forEach { leg ->
            when (val legType = leg.legType) {
                is TimedLegDto -> {
                    result = 31 * result + legType.service.publishedServiceName?.text.hashCode()
                    result = 31 * result + legType.legBoard.stopPointName.text.hashCode()
                    result = 31 * result + legType.legBoard.serviceDeparture.timetabledTime.hashCode()
                    result = 31 * result + legType.legAlight.serviceArrival.timetabledTime.hashCode()
                    result = 31 * result + legType.legAlight.stopPointName.text.hashCode()
                }

                is TransferLegDto -> {
                    result = 31 * result + legType.duration.hashCode()
                    result = 31 * result + legType.transferType.name.hashCode() //using string representation is mandatory!
                }
            }
        }

        return result
    }

}
