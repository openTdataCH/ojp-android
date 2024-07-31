package ch.opentransportdata.ojp.data.dto.response.tir.leg

import ch.opentransportdata.ojp.data.dto.converter.ConventionalModesOfOperationConverter
import ch.opentransportdata.ojp.data.dto.response.ModeDto
import ch.opentransportdata.ojp.data.dto.response.NameDto
import ch.opentransportdata.ojp.domain.model.ConventionalModesOfOperation
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

/**
 * Created by Michael Ruppen on 28.06.2024
 */
@Xml(name = "Service")
data class ServiceDto(
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
    val vehicleRef: String?
)