package ch.opentransportdata.ojp.data.dto.request.ser

import ch.opentransportdata.ojp.data.dto.OJP_NAME_SPACE
import ch.opentransportdata.ojp.data.dto.request.tr.IndividualTransportOptionDto
import ch.opentransportdata.ojp.data.dto.request.tr.PlaceReferenceDto
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName
import java.time.LocalDateTime

/**
 * Created by Deniz Kalem on 20.05.2026
 */
@Serializable
@XmlSerialName("Location", OJP_NAME_SPACE, "")
data class LocationDto(

    @XmlElement(true)
    @XmlSerialName("PlaceRef", OJP_NAME_SPACE, "")
    val placeReference: PlaceReferenceDto,

    @XmlElement(true)
    @XmlSerialName("DepArrTime", OJP_NAME_SPACE, "")
    @Contextual
    val depArrTime: LocalDateTime? = null,

    @XmlElement(true)
    @XmlSerialName("IndividualTransportOption", OJP_NAME_SPACE, "")
    val individualTransportOption: IndividualTransportOptionDto? = null,
)