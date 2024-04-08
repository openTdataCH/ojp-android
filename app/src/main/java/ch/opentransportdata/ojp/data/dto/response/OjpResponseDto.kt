package ch.opentransportdata.ojp.data.dto.response

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Xml

/**
 * Created by Michael Ruppen on 08.04.2024
 */
@Xml(name = "OJPResponse")
data class OjpResponseDto(
    @Element(name = "siri:ServiceDelivery")
    val serviceDelivery: ServiceDeliveryDto?
)
