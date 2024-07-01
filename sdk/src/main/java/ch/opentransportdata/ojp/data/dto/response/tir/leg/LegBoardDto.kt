package ch.opentransportdata.ojp.data.dto.response.tir.leg

import ch.opentransportdata.ojp.data.dto.response.NameDto
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

/**
 * Created by Michael Ruppen on 28.06.2024
 */
@Xml(name = "LegBoard")
data class LegBoardDto(
    @PropertyElement(name = "siri:StopPointRef")
    val stopPointRef: String,
    @Element(name = "StopPointName")
    val stopPointName: NameDto,
    @Element(name = "PlannedQuay")
    val plannedQuay: NameDto,
    @Element(name = "EstimatedQuay")
    val estimatedQuay: NameDto,
    @Element(name = "NameSuffix")
    val nameSuffix: NameDto?,
    @Element(name = "ServiceArrival")
    val serviceArrival: ServiceDepartureDto?,
    @Element(name = "ServiceDeparture")
    val serviceDeparture: ServiceDepartureDto,
    @PropertyElement(name = "Order")
    val order: Int?
    //todo: check for other needed elements...
)
