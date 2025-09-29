package ch.opentransportdata.ojp.data.dto.response.tir.leg

import android.os.Parcelable
import ch.opentransportdata.ojp.data.dto.OJP_NAME_SPACE
import ch.opentransportdata.ojp.data.dto.response.PlacesDto
import ch.opentransportdata.ojp.data.dto.response.tir.situations.PtSituationDto
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

/**
 * Created by Michael Ruppen on 28.06.2024
 */
@Parcelize
@Serializable
@XmlSerialName("TimedLeg", OJP_NAME_SPACE, "")
data class TimedLegDto(
    @XmlElement(true)
    @XmlSerialName("LegBoard", OJP_NAME_SPACE, "")
    val legBoard: LegBoardDto,

    @XmlElement(true)
    @XmlSerialName("LegIntermediate", OJP_NAME_SPACE, "")
    val legIntermediate: List<LegIntermediateDto>? = null,

    @XmlElement(true)
    @XmlSerialName("LegAlight", OJP_NAME_SPACE, "")
    val legAlight: LegAlightDto,

    @XmlElement(true)
    @XmlSerialName("Service", OJP_NAME_SPACE, "")
    val service: DatedJourneyDto,

    @XmlElement(true)
    @XmlSerialName("LegTrack", OJP_NAME_SPACE, "")
    val legTrack: LegTrackDto? = null
) : AbstractLegType(), Parcelable {

    val hasAnyPlatformChanges: Boolean
        get() = legBoard.isPlatformChanged || legAlight.isPlatformChanged

    /**
     * [DatedJourneyDto.cancelled] is for the whole service. [isCancelled] checks if the leg for the requested trip is cancelled
     */
    val isCancelled: Boolean
        get() = legBoard.notServicedStop == true || legAlight.notServicedStop == true

    val isPartCancelled: Boolean
        get() = legIntermediate?.any { it.notServicedStop == true } == true

    fun getPtSituationsForLeg(situations: List<PtSituationDto>): List<PtSituationDto> {
        val situationRefsOfTrip =
            this.service.situationFullRefWrapper?.situationFullRefs?.map { it.situationNumber } ?: emptyList()

        return situations.filter { situation -> situationRefsOfTrip.contains(situation.situationNumber) }
    }
}

fun TimedLegDto.minimalCopy(): TimedLegDto {
    return TimedLegDto(
        legBoard = legBoard.minimalCopy(),
        legIntermediate = legIntermediate?.map { it.minimalCopy() },
        legAlight = legAlight.minimalCopy(),
        service = service.minimalCopy(),
        legTrack = null
    )
}

fun TimedLegDto.replaceWithParentRef(places: PlacesDto): TimedLegDto {
    return this.copy(
        legBoard = legBoard.replaceWithParentRef(places),
        legIntermediate = legIntermediate?.map { it.replaceWithParentRef(places) },
        legAlight = legAlight.replaceWithParentRef(places)
    )
}