package ch.opentransportdata.ojp.data.dto.response.place

import ch.opentransportdata.ojp.data.dto.OJP_NAME_SPACE
import ch.opentransportdata.ojp.data.dto.response.NameDto
import ch.opentransportdata.ojp.data.dto.response.PrivateCodeDto
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

/**
 * Created by Michael Ruppen on 08.04.2024
 *
 * Serializable and Parcelize annotation is both needed for compose navigation with custom types
 */

@Parcelize
@Serializable
@XmlSerialName("StopPlace", OJP_NAME_SPACE, "")
data class StopPlaceDto(
    @XmlElement(true)
    @XmlSerialName("PrivateCode", OJP_NAME_SPACE, "")
    override val privateCodes: List<PrivateCodeDto>? = emptyList(),

    @XmlElement(true)
    @XmlSerialName("StopPlaceRef", OJP_NAME_SPACE, "")
    val stopPlaceRef: String,

    @XmlElement(true)
    @XmlSerialName("StopPlaceName", OJP_NAME_SPACE, "")
    val name: NameDto? = null,

    @XmlElement(true)
    @XmlSerialName("NameSuffix", OJP_NAME_SPACE, "")
    val nameSuffix: NameDto? = null,

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