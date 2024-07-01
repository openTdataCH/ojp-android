package ch.opentransportdata.ojp.data.dto.response.tir.leg

import ch.opentransportdata.ojp.data.dto.converter.TransferTypeConverter
import ch.opentransportdata.ojp.domain.model.TransferType
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

/**
 * Created by Michael Ruppen on 28.06.2024
 */
@Xml(name = "TransferLeg")
data class TransferLegDto(
    //todo: throws error:/Users/michaelruppen/AndroidStudioProjects/OJPAndroidSDK/sdk/build/tmp/kapt3/stubs/debug/ch/opentransportdata/ojp/data/dto/response/tir/leg/TransferLegDto.java:18: error: The constructor parameter 'transferType' in constructor TransferLegDto(java.util.List<? extends ch.opentransportdata.ojp.domain.model.TransferType>,ch.opentransportdata.ojp.data.dto.response.tir.leg.LegStartEndDto,ch.opentransportdata.ojp.data.dto.response.tir.leg.LegStartEndDto) in class ch.opentransportdata.ojp.data.dto.response.tir.leg.TransferLegDto is annotated with a TikXml annotation. Therefore a getter method with minimum package visibility with the name getTransferType() or isTransferType() in case of a boolean must be provided. Unfortunately, there is no such getter method. Please provide one!
    //    java.util.List<? extends ch.opentransportdata.ojp.domain.model.TransferType> transferType, @com.tickaroo.tikxml.annotation.Element(name = "LegStart")
//    @Element(name = "TransferType")
//    val transferType: List<TransferType>,
    @Element(name = "LegStart")
    val legStart: LegStartEndDto,
    @Element(name = "LegEnd")
    val legEnd: LegStartEndDto,
) : AbstractLegType()
