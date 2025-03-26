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
@Xml(name = "StopPoint")
data class StopPointDto(
    @Element(name = "PrivateCode")
    override val privateCodes: List<PrivateCodeDto>? = emptyList(),
    @PropertyElement(name = "siri:StopPointRef")
    val stopPointRef: String,
    @Element(name = "StopPointName")
    val stopPointName: NameDto,
    @Element(name = "NameSuffix")
    val nameSuffix: NameDto?,
    @Element(name = "PlannedQuay")
    val plannedQuay: NameDto?,
    @Element(name = "EstimatedQuay")
    val estimatedQuay: NameDto?,
    @PropertyElement(name = "ParentRef")
    val parentRef: String?,
    @PropertyElement(name = "TopographicPlaceRef")
    val topographicPlaceRef: String?,
    @PropertyElement(name = "WheelchairAccessible")
    val wheelchairAccessible: Boolean?,
    @PropertyElement(name = "Lighting")
    val lighting: Boolean?,
    @PropertyElement(name = "Covered")
    val covered: Boolean?
) : AbstractPlaceDto() {

    fun List<StopPointDto>?.findParentStation(stopPointRef: String): String {
        val match = this?.firstOrNull { it.stopPointRef == stopPointRef }
        return match?.parentRef ?: stopPointRef
    }
}