package ch.opentransportdata.ojp.data.dto.response.tir.leg

import android.os.Parcelable
import ch.opentransportdata.ojp.data.dto.response.tir.situations.PtSituationDto
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Xml
import kotlinx.parcelize.Parcelize

/**
 * Created by Michael Ruppen on 28.06.2024
 */
@Parcelize
@Xml(name = "TimedLeg")
data class TimedLegDto(
    @Element(name = "LegBoard")
    val legBoard: LegBoardDto,
    @Element(name = "LegIntermediate")
    val legIntermediate: List<LegIntermediateDto>?,
    @Element(name = "LegAlight")
    val legAlight: LegAlightDto,
    @Element(name = "Service")
    val service: DatedJourneyDto,
    @Element(name = "LegTrack")
    val legTrack: LegTrackDto?
) : AbstractLegType(), Parcelable {

    val hasAnyPlatformChanges: Boolean
        get() = legBoard.isPlatformChanged || legAlight.isPlatformChanged

    //temporary solution till backend fills data
    val isCancelled: Boolean
        get() = legBoard.notServicedStop == true && legAlight.notServicedStop == true

    val isPartCancelled: Boolean
        get() = legIntermediate?.any { it.notServicedStop == true } == true

    fun getPtSituationsForLeg(situations: List<PtSituationDto>): List<PtSituationDto> {
        val situationRefsOfTrip = this.service.situationFullRefWrapper?.situationFullRefs?.map { it.situationNumber } ?: emptyList()

        return situations.filter { situation -> situationRefsOfTrip.contains(situation.situationNumber) }
    }
}