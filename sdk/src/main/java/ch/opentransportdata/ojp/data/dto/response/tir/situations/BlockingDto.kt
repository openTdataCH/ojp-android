package ch.opentransportdata.ojp.data.dto.response.tir.situations

import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

/**
 * Created by Michael Ruppen on 28.06.2024
 */
@Xml(name = "siri:Blocking")
data class BlockingDto(
    @PropertyElement(name = "siri:JourneyPlanner")
    val journeyPlanner: Boolean
)
