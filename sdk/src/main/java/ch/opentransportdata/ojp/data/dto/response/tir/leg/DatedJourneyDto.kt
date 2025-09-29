package ch.opentransportdata.ojp.data.dto.response.tir.leg

import android.os.Parcelable
import ch.opentransportdata.ojp.data.dto.OJP_NAME_SPACE
import ch.opentransportdata.ojp.data.dto.SIRI_NAME_SPACE
import ch.opentransportdata.ojp.data.dto.SIRI_PREFIX
import ch.opentransportdata.ojp.data.dto.response.ModeDto
import ch.opentransportdata.ojp.data.dto.response.NameDto
import ch.opentransportdata.ojp.domain.model.ConventionalModesOfOperation
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

/**
 * Created by Michael Ruppen on 28.06.2024
 */

@Parcelize
@Serializable
@XmlSerialName("Service", OJP_NAME_SPACE, "")
data class DatedJourneyDto(
    @XmlElement(true)
    @XmlSerialName("Mode", OJP_NAME_SPACE, "")
    val mode: ModeDto,

    @XmlElement(true)
    @XmlSerialName("ConventionalModeOfOperation", OJP_NAME_SPACE, "")
    val conventionalModeOfOperation: ConventionalModesOfOperation? = null,

    @XmlElement(true)
    @XmlSerialName("TrainNumber", OJP_NAME_SPACE, "")
    val trainNumber: String? = null,

    @XmlElement(true)
    @XmlSerialName("LineRef", SIRI_NAME_SPACE, SIRI_PREFIX)
    val lineRef: String,

    @XmlElement(true)
    @XmlSerialName("OperatorRef", SIRI_NAME_SPACE, SIRI_PREFIX)
    val operatorRef: String? = null,

    @XmlElement(true)
    @XmlSerialName("PublicCode", OJP_NAME_SPACE, "")
    val publicCode: String? = null,

    @XmlElement(true)
    @XmlSerialName("PublishedServiceName", OJP_NAME_SPACE, "")
    val publishedServiceName: NameDto,

    @XmlElement(true)
    @XmlSerialName("ProductCategory", OJP_NAME_SPACE, "")
    val productCategory: ProductCategoryDto? = null,

    @XmlElement(true)
    @XmlSerialName("DirectionRef", SIRI_NAME_SPACE, SIRI_PREFIX)
    val directionRef: String? = null,

    @XmlElement(true)
    @XmlSerialName("OperatingDayRef", OJP_NAME_SPACE, "")
    val operatingDayRef: String,

    @XmlElement(true)
    @XmlSerialName("OriginStopPointRef", OJP_NAME_SPACE, "")
    val originStopPointRef: String? = null,

    @XmlElement(true)
    @XmlSerialName("DestinationStopPointRef", OJP_NAME_SPACE, "")
    val destinationStopPointRef: String? = null,

    @XmlElement(true)
    @XmlSerialName("OriginText", OJP_NAME_SPACE, "")
    val originText: NameDto,

    @XmlElement(true)
    @XmlSerialName("DestinationText", OJP_NAME_SPACE, "")
    val destinationText: NameDto? = null,

    @XmlElement(true)
    @XmlSerialName("JourneyRef", OJP_NAME_SPACE, "")
    val journeyRef: String,

    @XmlElement(true)
    @XmlSerialName("Attribute", OJP_NAME_SPACE, "")
    val attributes: List<AttributeDto>? = null,

    @XmlElement(true)
    @XmlSerialName("VehicleRef", SIRI_NAME_SPACE, SIRI_PREFIX)
    val vehicleRef: String? = null,

    @XmlElement(true)
    @XmlSerialName("SituationFullRefs", OJP_NAME_SPACE, "")
    val situationFullRefWrapper: SituationFullRefs? = null,

    @XmlElement(true)
    @XmlSerialName("Unplanned", OJP_NAME_SPACE, "")
    val unplanned: Boolean? = null,

    @XmlElement(true)
    @XmlSerialName("Cancelled", OJP_NAME_SPACE, "")
    val cancelled: Boolean? = null,

    @XmlElement(true)
    @XmlSerialName("Deviation", OJP_NAME_SPACE, "")
    val deviation: Boolean? = null
) : Parcelable {

    val isCarTrain: Boolean
        get() = lineRef.startsWith("atv")
}

fun DatedJourneyDto.minimalCopy(): DatedJourneyDto {
    return DatedJourneyDto(
        operatingDayRef = operatingDayRef,
        journeyRef = journeyRef,
        lineRef = lineRef,
        mode = mode,
        productCategory = productCategory,
        publishedServiceName = publishedServiceName,
        attributes = attributes,
        originText = originText,
        conventionalModeOfOperation = null,
        trainNumber = trainNumber,
        operatorRef = null,
        publicCode = null,
        directionRef = null,
        originStopPointRef = null,
        destinationStopPointRef = null,
        destinationText = null,
        vehicleRef = null,
        situationFullRefWrapper = null,
        unplanned = null,
        cancelled = null,
        deviation = null,
    )
}