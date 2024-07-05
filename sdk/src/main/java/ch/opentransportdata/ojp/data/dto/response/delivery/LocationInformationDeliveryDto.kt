package ch.opentransportdata.ojp.data.dto.response.delivery

import ch.opentransportdata.ojp.data.dto.response.PlaceResultDto
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml
import java.time.LocalDateTime

/**
 * Created by Michael Ruppen on 08.04.2024
 */
@Xml(name = "OJPLocationInformationDelivery")
internal data class LocationInformationDeliveryDto(
    @PropertyElement(name = "siri:ResponseTimestamp")
    override val responseTimestamp: LocalDateTime,
    @PropertyElement(name = "siri:RequestMessageRef")
    override val requestMessageRef: String?,
    @PropertyElement(name = "siri:DefaultLanguage")
    override val defaultLanguage: String?,
    @PropertyElement(name = "CalcTime")
    val calcTime: Int?,
    @Element(name = "PlaceResult")
    val placeResults: List<PlaceResultDto>? = emptyList(),
) : AbstractDeliveryDto()
