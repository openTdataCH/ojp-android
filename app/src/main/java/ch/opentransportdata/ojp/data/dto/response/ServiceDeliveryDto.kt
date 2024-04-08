package ch.opentransportdata.ojp.data.dto.response

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

/**
 * Created by Michael Ruppen on 08.04.2024
 */
@Xml(name = "ServiceDelivery")
data class ServiceDeliveryDto(
    @PropertyElement(name = "siri:ResponseTimestamp")
    val responseTimestamp: String? = null,

    @PropertyElement(name = "siri:ProducerRef")
    val producerRef: String? = null,

    @Element(name = "OJPLocationInformationDelivery")
    val locationInformation: LocationInformationResponseDto,
)
