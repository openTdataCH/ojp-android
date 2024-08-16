package ch.opentransportdata.ojp.data.dto.response.tir.leg

import android.os.Parcelable
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
) : AbstractLegType(), Parcelable{

    val hasAnyPlatformChanges: Boolean
        get() = legBoard.isPlatformChanged || legAlight.isPlatformChanged
}