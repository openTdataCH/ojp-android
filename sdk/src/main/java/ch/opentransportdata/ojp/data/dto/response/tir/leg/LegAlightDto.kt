package ch.opentransportdata.ojp.data.dto.response.tir.leg

import ch.opentransportdata.ojp.data.dto.response.NameDto
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

/**
 * Created by Michael Ruppen on 28.06.2024
 */
@Xml(name = "LegBoard")
data class LegAlightDto(
    @PropertyElement(name = "siri:StopPointRef")
    val stopPointRef: String,
    @Element(name = "StopPointName")
    val stopPointName: NameDto,
    @Element(name = "PlannedQuay")
    val plannedQuay: NameDto?,
    @Element(name = "EstimatedQuay")
    val estimatedQuay: NameDto?,
    @Element(name = "NameSuffix")
    val nameSuffix: NameDto?,
    @Element(name = "ServiceArrival")
    val serviceArrival: ServiceTimeDto,
    @Element(name = "ServiceDeparture")
    val serviceDeparture: ServiceTimeDto?,
    @PropertyElement(name = "Order")
    val order: Int?,
    @PropertyElement(name = "RequestStop")
    val requestStop: Boolean?,
    @PropertyElement(name = "UnplannedStop")
    val unplannedStop: Boolean?,
    @PropertyElement(name = "NotServicedStop")
    val notServicedStop: Boolean?,
    @PropertyElement(name = "NoBoardingAtStop")
    val noBoardingAtStop: Boolean?,
    @PropertyElement(name = "NoAlightingAtStop")
    val noAlightingAtStop: Boolean?,
) {
    val isPlatformChanged: Boolean
        get() = estimatedQuay != null && plannedQuay?.text != estimatedQuay.text

}
