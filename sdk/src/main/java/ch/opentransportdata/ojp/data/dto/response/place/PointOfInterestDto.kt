package ch.opentransportdata.ojp.data.dto.response.place

import ch.opentransportdata.ojp.data.dto.OJP_NAME_SPACE
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
@XmlSerialName("PointOfInterest", OJP_NAME_SPACE, "")
data class PointOfInterestDto(
    @XmlElement(true)
    @XmlSerialName("PrivateCode", OJP_NAME_SPACE, "")
    override val privateCodes: List<PrivateCodeDto>? = emptyList(),

    @XmlElement(true)
    @XmlSerialName("PublicCode", OJP_NAME_SPACE, "")
    val publicCode: String,

    @XmlElement(true)
    @XmlSerialName("Name", OJP_NAME_SPACE, "")
    val name: NameDto,

    @XmlElement(true)
    @XmlSerialName("NameSuffix", OJP_NAME_SPACE, "")
    val nameSuffix: NameDto? = null,

    @XmlElement(true)
    @XmlSerialName("TopographicPlaceRef", OJP_NAME_SPACE, "")
    val topographicPlaceRef: String? = null
) : AbstractPlaceDto()