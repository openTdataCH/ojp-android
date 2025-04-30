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
@Xml(name = "StopPlace")
data class StopPlaceDto(
    @Element(name = "PrivateCode")
    override val privateCodes: List<PrivateCodeDto>? = emptyList(),
    @PropertyElement(name = "StopPlaceRef")
    val stopPlaceRef: String,
    @Element(name = "StopPlaceName")
    val name: NameDto?,
    @Element(name = "NameSuffix")
    val nameSuffix: NameDto?,
    @PropertyElement(name = "TopographicPlaceRef")
    val topographicPlaceRef: String? = null,
    @PropertyElement(name = "WheelchairAccessible")
    val wheelchairAccessible: Boolean? = null,
    @PropertyElement(name = "Lighting")
    val lighting: Boolean? = null,
    @PropertyElement(name = "Covered")
    val covered: Boolean? = null
) : AbstractPlaceDto()