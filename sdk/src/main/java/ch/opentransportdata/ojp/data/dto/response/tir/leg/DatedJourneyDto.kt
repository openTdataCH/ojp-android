package ch.opentransportdata.ojp.data.dto.response.tir.leg

import android.os.Parcelable
import ch.opentransportdata.ojp.data.dto.response.ModeDto
import ch.opentransportdata.ojp.data.dto.response.NameDto
import ch.opentransportdata.ojp.domain.model.ConventionalModesOfOperation
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml
import kotlinx.parcelize.Parcelize

/**
 * Created by Michael Ruppen on 28.06.2024
 */
@Parcelize
@Xml(name = "Service")
data class DatedJourneyDto(
    @Element(name = "Mode")
    val mode: ModeDto,
    @PropertyElement(name = "ConventionalModeOfOperation")
    val conventionalModeOfOperation: ConventionalModesOfOperation?,
    @PropertyElement(name = "TrainNumber")
    val trainNumber: String?,
    @PropertyElement(name = "siri:LineRef")
    val lineRef: String,
    @PropertyElement(name = "siri:OperatorRef")
    val operatorRef: String?,
    @PropertyElement(name = "PublicCode")
    val publicCode: String?,
    @Element(name = "PublishedServiceName")
    val publishedServiceName: NameDto,
    @Element(name = "ProductCategory")
    val productCategory: ProductCategoryDto?,
    @PropertyElement(name = "siri:DirectionRef")
    val directionRef: String?,
    @PropertyElement(name = "OperatingDayRef")
    val operatingDayRef: String,
    @PropertyElement(name = "OriginStopPointRef")
    val originStopPointRef: String?,
    @PropertyElement(name = "DestinationStopPointRef")
    val destinationStopPointRef: String?,
    @Element(name = "OriginText")
    val originText: NameDto,
    @Element(name = "DestinationText")
    val destinationText: NameDto?,
    @PropertyElement(name = "JourneyRef")
    val journeyRef: String,
    @Element(name = "Attribute")
    val attributes: List<AttributeDto>?,
    @PropertyElement(name = "siri:VehicleRef")
    val vehicleRef: String?,
    @Element(name = "SituationFullRefs")
    val situationFullRefWrapper: SituationFullRefs?,
    @PropertyElement(name = "Unplanned")
    val unplanned: Boolean?, //not yet delivered from backend
    @PropertyElement(name = "Cancelled")
    val cancelled: Boolean?,
    @PropertyElement(name = "Deviation")
    val deviation: Boolean?
) : Parcelable {

    //workaround till pt-subModes get delivered correctly
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