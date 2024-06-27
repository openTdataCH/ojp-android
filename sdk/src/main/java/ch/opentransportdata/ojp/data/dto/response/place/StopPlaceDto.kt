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
 */
@Serializable
@Parcelize
@Xml(name = "StopPlace")
data class StopPlaceDto(
    @PropertyElement(name = "StopPlaceRef")
    val stopPlaceRef: String,
    @Element(name = "StopPlaceName")
    val name: NameDto,
    @PropertyElement(name = "TopographicPlaceRef")
    val topographicPlaceRef: String?,
    @Element(name = "PrivateCode")
    override val privateCodes: List<PrivateCodeDto>? = emptyList()
) : AbstractPlaceDto()
