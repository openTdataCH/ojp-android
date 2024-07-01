package ch.opentransportdata.ojp.data.dto.response.tir.situations

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Xml

/**
 * Created by Michael Ruppen on 28.06.2024
 */
@Xml(name = "siri:StopPoints")
data class StopPointsDto(
    @Element(name = "siri:AffectedStopPoint")
    val affectedStops: List<AffectedStopPointDto>
)
