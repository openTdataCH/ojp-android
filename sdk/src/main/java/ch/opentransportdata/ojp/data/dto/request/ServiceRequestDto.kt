package ch.opentransportdata.ojp.data.dto.request

import ch.opentransportdata.ojp.data.dto.request.lir.LocationInformationRequestDto
import ch.opentransportdata.ojp.data.dto.request.tir.TripRequestDto
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml
import java.time.LocalDateTime

/**
 * Created by Michael Ruppen on 08.04.2024
 */
@Xml(name = "ServiceRequest")
internal data class ServiceRequestDto(
    @PropertyElement(name = "siri:RequestTimestamp")
    val requestTimestamp: LocalDateTime,

    @PropertyElement(name = "siri:RequestorRef")
    val requestorRef: String,

    @Element(name = "OJPLocationInformationRequest")
    val locationInformationRequest: LocationInformationRequestDto? = null,

    @Element(name= "OJPTripRequest")
    val tripRequest: TripRequestDto? = null,
)