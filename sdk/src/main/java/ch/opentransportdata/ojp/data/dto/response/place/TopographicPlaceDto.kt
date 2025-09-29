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
@XmlSerialName("TopographicPlace", OJP_NAME_SPACE, "")
data class TopographicPlaceDto(
    @XmlElement(true)
    @XmlSerialName("PrivateCode", OJP_NAME_SPACE, "")
    override val privateCodes: List<PrivateCodeDto>? = emptyList(),

    @XmlElement(true)
    @XmlSerialName("TopographicPlaceRef", OJP_NAME_SPACE, "")
    val ref: String? = null,

    @XmlElement(true)
    @XmlSerialName("TopographicPlaceName", OJP_NAME_SPACE, "")
    val name: NameDto
) : AbstractPlaceDto()