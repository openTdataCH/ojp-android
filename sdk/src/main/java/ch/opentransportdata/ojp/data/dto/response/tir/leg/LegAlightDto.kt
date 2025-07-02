package ch.opentransportdata.ojp.data.dto.response.tir.leg

import android.os.Parcelable
import ch.opentransportdata.ojp.data.dto.response.NameDto
import ch.opentransportdata.ojp.data.dto.response.PlacesDto
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml
import kotlinx.parcelize.Parcelize

/**
 * Created by Michael Ruppen on 28.06.2024
 */
@Parcelize
@Xml(name = "LegAlight")
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
) : Parcelable {

    val isPlatformChanged: Boolean
        get() = estimatedQuay != null && plannedQuay?.text != estimatedQuay.text

    val mergedQuay: NameDto?
        get() = estimatedQuay ?: plannedQuay
}

fun LegAlightDto.minimalCopy(): LegAlightDto {
    return LegAlightDto(
        stopPointRef = stopPointRef,
        stopPointName = stopPointName,
        serviceArrival = ServiceTimeDto(
            timetabledTime = serviceArrival.timetabledTime,
            estimatedTime = serviceArrival.estimatedTime
        ),
        plannedQuay = null,
        estimatedQuay = null,
        nameSuffix = null,
        serviceDeparture = null,
        order = null,
        requestStop = null,
        unplannedStop = null,
        notServicedStop = null,
        noBoardingAtStop = null,
        noAlightingAtStop = null,
    )
}

fun LegAlightDto.replaceWithParentRef(places: PlacesDto): LegAlightDto {
    return this.copy(stopPointRef = places.findParentStation(stopPointRef))
}