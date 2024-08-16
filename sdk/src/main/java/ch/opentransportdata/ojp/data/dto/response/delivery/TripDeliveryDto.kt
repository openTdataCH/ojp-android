package ch.opentransportdata.ojp.data.dto.response.delivery

import ch.opentransportdata.ojp.data.dto.response.tir.TripResponseContextDto
import ch.opentransportdata.ojp.data.dto.response.tir.TripResultDto
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml
import java.time.LocalDateTime

/**
 * Created by Michael Ruppen on 14.05.2024
 */

@Xml(name = "OJPTripDelivery")
data class TripDeliveryDto(
    @PropertyElement(name = "siri:ResponseTimestamp")
    override val responseTimestamp: LocalDateTime,
    @PropertyElement(name = "siri:RequestMessageRef")
    override val requestMessageRef: String?,
    @PropertyElement(name = "siri:DefaultLanguage")
    override val defaultLanguage: String?,
    @PropertyElement(name = "CalcTime")
    val calcTime: Int?,
    @Element(name = "TripResult")
    val tripResults: List<TripResultDto>?,
    @Element(name = "TripResponseContext")
    val responseContext: TripResponseContextDto?
) : AbstractDeliveryDto()