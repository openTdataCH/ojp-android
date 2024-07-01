package ch.opentransportdata.ojp.data.dto.response.tir.leg

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Xml

/**
 * Created by Michael Ruppen on 28.06.2024
 */
@Xml(name = "TimedLeg")
data class TimedLegDto(
    @Element(name = "LegBoard")
    val legBoard: LegBoardDto,
    @Element(name = "LegIntermediate")
    val legIntermediate: List<LegIntermediateDto>?,
    @Element(name = "LegAlight")
    val legAlight: LegAlightDto,
    @Element(name = "Service")
    val service: ServiceDto,
    @Element(name = "LegTrack")
    val legTrack: LegTrackDto
) : AbstractLegType()
