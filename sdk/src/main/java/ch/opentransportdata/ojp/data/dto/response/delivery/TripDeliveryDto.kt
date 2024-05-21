package ch.opentransportdata.ojp.data.dto.response.delivery

import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

/**
 * Created by Michael Ruppen on 14.05.2024
 */

@Xml(name = "OJPTripDelivery")
internal data class TripDeliveryDto(
    @PropertyElement(name = "siri:ResponseTimestamp")
    override val responseTimestamp: String,
    @PropertyElement(name = "siri:RequestMessageRef")
    override val requestMessageRef: String?,
    @PropertyElement(name = "siri:DefaultLanguage")
    override val defaultLanguage: String?,
) : AbstractDeliveryDto()