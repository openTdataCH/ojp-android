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
@XmlSerialName("LegAlight", OJP_NAME_SPACE, "")
data class LegAlightDto(
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
    val serviceArrival: ServiceTimeDto,

    @XmlElement(true)
    @XmlSerialName("ServiceDeparture", OJP_NAME_SPACE, "")
    val serviceDeparture: ServiceTimeDto? = null,

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
    val noAlightingAtStop: Boolean? = null
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