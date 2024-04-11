package ch.opentransportdata.ojp.data.dto.request

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Xml

/**
 * Created by Michael Ruppen on 08.04.2024
 */
@Xml(name = "OJPRequest")
data class OjpRequestDto(
    @Element(name = "siri:ServiceRequest")
    val serviceRequest: ServiceRequestDto?
)