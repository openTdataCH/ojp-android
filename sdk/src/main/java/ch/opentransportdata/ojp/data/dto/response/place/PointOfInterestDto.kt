package ch.opentransportdata.ojp.data.dto.response.place

import ch.opentransportdata.ojp.data.dto.response.NameDto
import ch.opentransportdata.ojp.data.dto.response.PrivateCodeDto
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

/**
 * Created by Deniz Kalem on 25.03.2025
 */
@Serializable
@Parcelize
@Xml(name = "PointOfInterest")
data class PointOfInterestDto(
    @Element(name = "PrivateCode")
    override val privateCodes: List<PrivateCodeDto>? = emptyList(),
    @PropertyElement(name = "PublicCode")
    val publicCode: String,
    @Element(name = "Name")
    val name: NameDto,
    @Element(name = "NameSuffix")
    val nameSuffix: NameDto?,
    @PropertyElement(name = "TopographicPlaceRef")
    val topographicPlaceRef: String?
) : AbstractPlaceDto()