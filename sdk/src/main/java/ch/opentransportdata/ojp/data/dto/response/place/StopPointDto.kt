package ch.opentransportdata.ojp.data.dto.response.place

import ch.opentransportdata.ojp.data.dto.OJP_NAME_SPACE
import ch.opentransportdata.ojp.data.dto.SIRI_NAME_SPACE
import ch.opentransportdata.ojp.data.dto.SIRI_PREFIX
import ch.opentransportdata.ojp.data.dto.response.NameDto
import ch.opentransportdata.ojp.data.dto.response.PrivateCodeDto
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

/**
 * Created by Deniz Kalem on 25.03.2025
 */
@Parcelize
@Serializable
@XmlSerialName("StopPoint", OJP_NAME_SPACE, "")
data class StopPointDto(
    @XmlElement(true)
    @XmlSerialName("PrivateCode", OJP_NAME_SPACE, "")
    override val privateCodes: List<PrivateCodeDto>? = emptyList(),

    @XmlElement(true)
    @XmlSerialName("StopPointRef", SIRI_NAME_SPACE, SIRI_PREFIX)
    val stopPointRef: String,

    @XmlElement(true)
    @XmlSerialName("StopPointName", OJP_NAME_SPACE, "")
    val stopPointName: NameDto? = null,

    @XmlElement(true)
    @XmlSerialName("NameSuffix", OJP_NAME_SPACE, "")
    val nameSuffix: NameDto? = null,

    @XmlElement(true)
    @XmlSerialName("PlannedQuay", OJP_NAME_SPACE, "")
    val plannedQuay: NameDto? = null,

    @XmlElement(true)
    @XmlSerialName("EstimatedQuay", OJP_NAME_SPACE, "")
    val estimatedQuay: NameDto? = null,

    @XmlElement(true)
    @XmlSerialName("ParentRef", OJP_NAME_SPACE, "")
    val parentRef: String? = null,

    @XmlElement(true)
    @XmlSerialName("TopographicPlaceRef", OJP_NAME_SPACE, "")
    val topographicPlaceRef: String? = null,

    @XmlElement(true)
    @XmlSerialName("WheelchairAccessible", OJP_NAME_SPACE, "")
    val wheelchairAccessible: Boolean? = null,

    @XmlElement(true)
    @XmlSerialName("Lighting", OJP_NAME_SPACE, "")
    val lighting: Boolean? = null,

    @XmlElement(true)
    @XmlSerialName("Covered", OJP_NAME_SPACE, "")
    val covered: Boolean? = null
) : AbstractPlaceDto()