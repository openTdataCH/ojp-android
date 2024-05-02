package ch.opentransportdata.ojp.data.dto.response

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

/**
 * Created by Michael Ruppen on 08.04.2024
 */
@Xml(name = "OJPLocationInformationDelivery")
internal data class LocationInformationResponseDto(
    @PropertyElement(name = "siri:ResponseTimestamp")
    val responseTimestamp: String,
    @PropertyElement(name = "siri:RequestMessageRef")
    val requestMessageRef: String?,
    @PropertyElement(name = "siri:DefaultLanguage")
    val defaultLanguage: String?,
    @PropertyElement(name = "CalcTime")
    val calcTime: Int?,
    @Element(name = "PlaceResult")
    val placeResults: List<PlaceResultDto>? = emptyList(),
)
