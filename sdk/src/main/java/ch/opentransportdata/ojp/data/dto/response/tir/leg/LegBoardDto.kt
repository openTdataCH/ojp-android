package ch.opentransportdata.ojp.data.dto.response.tir.leg

import android.os.Parcelable
import ch.opentransportdata.ojp.data.dto.OJP_NAME_SPACE
import ch.opentransportdata.ojp.data.dto.SIRI_NAME_SPACE
import ch.opentransportdata.ojp.data.dto.SIRI_PREFIX
import ch.opentransportdata.ojp.data.dto.response.NameDto
import ch.opentransportdata.ojp.data.dto.response.PlacesDto
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

/**
 * Created by Michael Ruppen on 28.06.2024
 */
@Parcelize
@Serializable
@XmlSerialName("LegBoard", OJP_NAME_SPACE, "")
data class LegBoardDto(

    @XmlElement(true)
    @XmlSerialName("StopPointRef", SIRI_NAME_SPACE, SIRI_PREFIX)
    val stopPointRef: String,

    @XmlElement(true)
    @XmlSerialName("StopPointName", OJP_NAME_SPACE, "")
    val stopPointName: NameDto,

    @XmlElement(true)
    @XmlSerialName("PlannedQuay", OJP_NAME_SPACE, "")
    val plannedQuay: NameDto? = null,

    @XmlElement(true)
    @XmlSerialName("EstimatedQuay", OJP_NAME_SPACE, "")
    val estimatedQuay: NameDto? = null,

    @XmlElement(true)
    @XmlSerialName("NameSuffix", OJP_NAME_SPACE, "")
    val nameSuffix: NameDto? = null,

    @XmlElement(true)
    @XmlSerialName("ServiceArrival", OJP_NAME_SPACE, "")
    val serviceArrival: ServiceTimeDto? = null,

    @XmlElement(true)
    @XmlSerialName("ServiceDeparture", OJP_NAME_SPACE, "")
    val serviceDeparture: ServiceTimeDto,

    @XmlElement(true)
    @XmlSerialName("Order", OJP_NAME_SPACE, "")
    val order: Int? = null,

    @XmlElement(true)
    @XmlSerialName("RequestStop", OJP_NAME_SPACE, "")
    val requestStop: Boolean? = null,

    @XmlElement(true)
    @XmlSerialName("UnplannedStop", OJP_NAME_SPACE, "")
    val unplannedStop: Boolean? = null,

    @XmlElement(true)
    @XmlSerialName("NotServicedStop", OJP_NAME_SPACE, "")
    val notServicedStop: Boolean? = null,

    @XmlElement(true)
    @XmlSerialName("NoBoardingAtStop", OJP_NAME_SPACE, "")
    val noBoardingAtStop: Boolean? = null,

    @XmlElement(true)
    @XmlSerialName("NoAlightingAtStop", OJP_NAME_SPACE, "")
    val noAlightingAtStop: Boolean? = null,

    @XmlElement(true)
    @XmlSerialName("ExpectedDepartureOccupancy", SIRI_NAME_SPACE, SIRI_PREFIX)
    val expectedDepartureOccupancy: List<ExpectedDepartureOccupancyDto>? = null
) : Parcelable {

    val isPlatformChanged: Boolean
        get() = estimatedQuay != null && plannedQuay?.text != estimatedQuay.text

    val mergedQuay: NameDto?
        get() = estimatedQuay ?: plannedQuay
}

fun LegBoardDto.minimalCopy(): LegBoardDto {
    return LegBoardDto(
        stopPointRef = stopPointRef,
        stopPointName = stopPointName,
        serviceDeparture = ServiceTimeDto(
            timetabledTime = serviceDeparture.timetabledTime,
            estimatedTime = serviceDeparture.estimatedTime
        ),
        plannedQuay = plannedQuay,
        estimatedQuay = null,
        nameSuffix = null,
        serviceArrival = null,
        order = null,
        requestStop = null,
        unplannedStop = null,
        notServicedStop = null,
        noBoardingAtStop = null,
        noAlightingAtStop = null,
        expectedDepartureOccupancy = emptyList()
    )
}

fun LegBoardDto.replaceWithParentRef(places: PlacesDto): LegBoardDto {
    return this.copy(stopPointRef = places.findParentStation(stopPointRef))
}