package ch.opentransportdata.ojp.data.dto

import ch.opentransportdata.ojp.data.dto.request.OjpRequestDto
import ch.opentransportdata.ojp.data.dto.response.OjpResponseDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.ExperimentalXmlUtilApi
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlNamespaceDeclSpecs
import nl.adaptivity.xmlutil.serialization.XmlSerialName

/**
 * Created by Michael Ruppen on 08.04.2024
 */

@OptIn(ExperimentalXmlUtilApi::class)
@Serializable
@XmlSerialName("OJP", OJP_NAME_SPACE, "")
@XmlNamespaceDeclSpecs(
    "siri=http://www.siri.org.uk/siri",
    "xsi=http://www.w3.org/2001/XMLSchema-instance",
    "xsd=http://www.w3.org/2001/XMLSchema"
)
internal data class OjpDto(

    @XmlElement(true)
    @XmlSerialName("OJPRequest", OJP_NAME_SPACE, "")
    val ojpRequest: OjpRequestDto? = null,

    @XmlElement(true)
    @XmlSerialName("OJPResponse", OJP_NAME_SPACE, "")
    val ojpResponse: OjpResponseDto? = null,

    @XmlElement(false)
    @SerialName("version")
    val version: String = "2.0",
)

internal const val OJP_NAME_SPACE = "http://www.vdv.de/ojp"
internal const val SIRI_NAME_SPACE = "http://www.siri.org.uk/siri"
internal const val SIRI_PREFIX = "siri"