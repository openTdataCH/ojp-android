package ch.opentransportdata.ojp.data.dto.request

import ch.opentransportdata.ojp.data.dto.OJP_NAME_SPACE
import ch.opentransportdata.ojp.data.dto.SIRI_NAME_SPACE
import ch.opentransportdata.ojp.data.dto.SIRI_PREFIX
import ch.opentransportdata.ojp.data.dto.request.lir.LocationInformationRequestDto
import ch.opentransportdata.ojp.data.dto.request.tir.TripRequestDto
import ch.opentransportdata.ojp.data.dto.request.trr.TripRefineRequestDto
import kotlinx.serialization.Contextual
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName
import java.time.LocalDateTime

/**
 * Created by Michael Ruppen on 08.04.2024
 */
@kotlinx.serialization.Serializable
@XmlSerialName("ServiceRequest", SIRI_NAME_SPACE, SIRI_PREFIX)
internal data class ServiceRequestDto(

    @XmlElement(true)
    @XmlSerialName("ServiceRequestContext", SIRI_NAME_SPACE, SIRI_PREFIX)
    val serviceRequestContext: ServiceRequestContextDto,

    @XmlElement(true)
    @XmlSerialName("RequestTimestamp", SIRI_NAME_SPACE, SIRI_PREFIX)
    @Contextual
    val requestTimestamp: LocalDateTime,

    @XmlElement(true)
    @XmlSerialName("RequestorRef", SIRI_NAME_SPACE, SIRI_PREFIX)
    val requestorRef: String,

    @XmlElement(true)
    @XmlSerialName("OJPLocationInformationRequest", OJP_NAME_SPACE, "ojp")
    val locationInformationRequest: LocationInformationRequestDto? = null,

    @XmlElement(true)
    @XmlSerialName("OJPTripRequest", OJP_NAME_SPACE, "ojp")
    val tripRequest: TripRequestDto? = null,

    @XmlElement(true)
    @XmlSerialName("OJPTripRefineRequest", OJP_NAME_SPACE, "ojp")
    val tripRefineRequest: TripRefineRequestDto? = null,
)