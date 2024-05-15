package ch.opentransportdata.ojp.data.dto.response.place

import ch.opentransportdata.ojp.data.dto.response.NameDto
import ch.opentransportdata.ojp.data.dto.response.PrivateCodeDto
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

/**
 * Created by Michael Ruppen on 08.04.2024
 */
@Xml(name = "Address")
data class AddressDto(
    @PropertyElement(name = "PublicCode")
    val publicCode: String,
    @Element(name = "StopPlaceName")
    val name: NameDto,
    @PropertyElement(name = "CountryName")
    val countryName: String?,
    @PropertyElement(name = "PostCode")
    val postCode: String?,
    @PropertyElement(name = "TopographicPlaceName")
    val topographicPlaceName: String?,
    @PropertyElement(name = "TopographicPlaceRef")
    val topographicPlaceRef: String?,
    @PropertyElement(name = "Street")
    val street: String?,
    @PropertyElement(name = "HouseNumber")
    val houseNumber: String?,
    @PropertyElement(name = "CrossRoad")
    val crossRoad: String?,
    @Element(name = "PrivateCode")
    override val privateCodes: List<PrivateCodeDto> = emptyList()
) : AbstractPlaceDto()
