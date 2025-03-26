package ch.opentransportdata.ojp.data.dto.response.place

import ch.opentransportdata.ojp.data.dto.response.NameDto
import ch.opentransportdata.ojp.data.dto.response.PrivateCodeDto
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

/**
 * Created by Michael Ruppen on 08.04.2024
 *
 * Serializable and Parcelize annotation is both needed for compose navigation with custom types
 */
@Serializable
@Parcelize
@Xml(name = "Address")
data class AddressDto(
    @Element(name = "PrivateCode")
    override val privateCodes: List<PrivateCodeDto>? = emptyList(),
    @PropertyElement(name = "PublicCode")
    val publicCode: String,
    @Element(name = "Name")
    val name: NameDto,
    @Element(name = "NameSuffix")
    val nameSuffix: NameDto? = null,
    @PropertyElement(name = "CountryName")
    val countryName: String? = null,
    @PropertyElement(name = "PostCode")
    val postCode: String? = null,
    @Element(name = "TopographicPlaceName")
    val topographicPlaceName: NameDto? = null,
    @PropertyElement(name = "TopographicPlaceRef")
    val topographicPlaceRef: String? = null,
    @PropertyElement(name = "Street")
    val street: String? = null,
    @PropertyElement(name = "HouseNumber")
    val houseNumber: String? = null,
    @PropertyElement(name = "CrossRoad")
    val crossRoad: String? = null
) : AbstractPlaceDto()
