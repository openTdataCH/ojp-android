package ch.opentransportdata.ojp.data.dto.response.tir.leg

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Xml

/**
 * Created by Michael Ruppen on 28.06.2024
 */
@Xml(name = "LegTrack")
data class LegTrackDto(
    @Element(name = "TrackSection")
    val trackSection: List<TrackSectionDto>
)
