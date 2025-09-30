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
@XmlSerialName("Address", OJP_NAME_SPACE, "")
data class AddressDto(
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
    @XmlSerialName("CountryName", OJP_NAME_SPACE, "")
    val countryName: String? = null,

    @XmlElement(true)
    @XmlSerialName("PostCode", OJP_NAME_SPACE, "")
    val postCode: String? = null,

    @XmlElement(true)
    @XmlSerialName("TopographicPlaceName", OJP_NAME_SPACE, "")
    val topographicPlaceName: NameDto? = null,

    @XmlElement(true)
    @XmlSerialName("TopographicPlaceRef", OJP_NAME_SPACE, "")
    val topographicPlaceRef: String? = null,

    @XmlElement(true)
    @XmlSerialName("Street", OJP_NAME_SPACE, "")
    val street: String? = null,

    @XmlElement(true)
    @XmlSerialName("HouseNumber", OJP_NAME_SPACE, "")
    val houseNumber: String? = null,

    @XmlElement(true)
    @XmlSerialName("CrossRoad", OJP_NAME_SPACE, "")
    val crossRoad: String? = null
) : AbstractPlaceDto()
