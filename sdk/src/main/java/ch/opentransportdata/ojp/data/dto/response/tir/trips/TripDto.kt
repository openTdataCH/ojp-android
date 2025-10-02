package ch.opentransportdata.ojp.data.dto.response.tir.trips

import android.os.Parcelable
import ch.opentransportdata.ojp.data.dto.OJP_NAME_SPACE
import ch.opentransportdata.ojp.data.dto.converter.DurationSerializer
import ch.opentransportdata.ojp.data.dto.response.PlacesDto
import ch.opentransportdata.ojp.data.dto.response.tir.LegDto
import ch.opentransportdata.ojp.data.dto.response.tir.leg.ContinuousLegDto
import ch.opentransportdata.ojp.data.dto.response.tir.leg.TimedLegDto
import ch.opentransportdata.ojp.data.dto.response.tir.leg.TransferLegDto
import ch.opentransportdata.ojp.data.dto.response.tir.minimalCopy
import ch.opentransportdata.ojp.data.dto.response.tir.replaceWithParentRef
import ch.opentransportdata.ojp.data.dto.response.tir.situations.PtSituationDto
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName
import java.time.Duration
import java.time.LocalDateTime

/**
 * Created by Michael Ruppen on 28.06.2024
 */

@Parcelize
@Serializable
@XmlSerialName("Trip", OJP_NAME_SPACE, "")
data class TripDto(
    @XmlElement(true)
    @XmlSerialName("Id", OJP_NAME_SPACE, "")
    override val id: String,

    @XmlElement(true)
    @XmlSerialName("Duration", OJP_NAME_SPACE, "")
    @Serializable(with = DurationSerializer::class)
    val duration: Duration,

    @XmlElement(true)
    @XmlSerialName("StartTime", OJP_NAME_SPACE, "")
    @Contextual
    val startTime: LocalDateTime,

    @XmlElement(true)
    @XmlSerialName("EndTime", OJP_NAME_SPACE, "")
    @Contextual
    val endTime: LocalDateTime,

    @XmlElement(true)
    @XmlSerialName("Transfers", OJP_NAME_SPACE, "")
    val transfers: Int,

    @XmlElement(true)
    @XmlSerialName("Leg", OJP_NAME_SPACE, "")
    val legs: List<LegDto>,

    @XmlElement(true)
    @XmlSerialName("Unplanned", OJP_NAME_SPACE, "")
    val unplanned: Boolean? = null,

    @XmlElement(true)
    @XmlSerialName("Delayed", OJP_NAME_SPACE, "")
    val delayed: Boolean? = null,

    @XmlElement(true)
    @XmlSerialName("Infeasible", OJP_NAME_SPACE, "")
    val infeasible: Boolean? = null
) : AbstractTripDto(), Parcelable {

    val startWithTransferLeg: Boolean
        get() = legs.first().legType is TransferLegDto

    val endWithTransferLeg: Boolean
        get() = legs.last().legType is TransferLegDto

    val startWithContinuousLeg: Boolean
        get() = legs.first().legType is ContinuousLegDto

    val endWithContinuousLeg: Boolean
        get() = legs.last().legType is ContinuousLegDto

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

    val isCarTrainTrip: Boolean
        get() = legs.any { (it.legType as? TimedLegDto)?.service?.isCarTrain == true }

    /**
     * If the trip has [TimedLegDto.isCancelled], [isInfeasible] or [TimedLegDto.hasAnyPlatformChanges]
     * set to true, it is marked to have disruptions
     */
    val hasAnyDisruption: Boolean
        get() = legs.any { (it.legType as? TimedLegDto)?.isCancelled == true }
                || isInfeasible
//              || deviation == true
                || legs.any { (it.legType as? TimedLegDto)?.hasAnyPlatformChanges == true }

    /**
     * [infeasible] flag is also set when trip is cancelled. [isInfeasible] shows if the trip is infeasible but not cancelled
     */
    val isInfeasible: Boolean
        get() = !isCancelled && infeasible == true

    val isCancelled: Boolean
        get() = legs.any { (it.legType as? TimedLegDto)?.isCancelled == true }

    fun getPtSituationsForTrip(situations: List<PtSituationDto>): List<PtSituationDto> {
        return legs.mapNotNull { it.legType as? TimedLegDto }.flatMap { it.getPtSituationsForLeg(situations) }.distinct()
    }

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
                    result = 31 * result + legType.service.publishedServiceName.text.hashCode()
                    result = 31 * result + legType.legBoard.stopPointName.text.hashCode()
                    result = 31 * result + legType.legBoard.serviceDeparture.timetabledTime.hashCode()
                    result = 31 * result + legType.legAlight.serviceArrival.timetabledTime.hashCode()
                    result = 31 * result + legType.legAlight.stopPointName.text.hashCode()
                    legType.service.trainNumber?.let { result = 31 * result + it.hashCode() }
                }

                is TransferLegDto -> {
                    result = 31 * result + legType.duration.toString().hashCode()
                    result = 31 * result + legType.transferType.name.hashCode() //using string representation is mandatory!
                }
            }
        }

        return result
    }
}

fun TripDto.minimalCopy(places: PlacesDto?): TripDto {
    return TripDto(
        id = id,
        duration = duration,
        startTime = startTime,
        endTime = endTime,
        transfers = transfers,
        legs = places?.let { p -> legs.map { it.minimalCopy().replaceWithParentRef(p) } } ?: legs.map { it.minimalCopy() },
        unplanned = null,
        delayed = null,
        infeasible = null,
    )
}