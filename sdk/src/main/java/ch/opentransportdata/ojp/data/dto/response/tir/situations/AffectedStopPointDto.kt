package ch.opentransportdata.ojp.data.dto.response.tir.situations

import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

/**
 * Created by Michael Ruppen on 28.06.2024
 */
@Xml(name = "siri:AffectedStopPoint")
data class AffectedStopPointDto(
    @PropertyElement(name = "siri:StopPointRef")
    val stopPointRef: String?
)
