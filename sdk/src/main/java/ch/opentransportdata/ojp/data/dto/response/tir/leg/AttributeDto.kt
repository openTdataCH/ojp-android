package ch.opentransportdata.ojp.data.dto.response.tir.leg

import ch.opentransportdata.ojp.data.dto.response.NameDto
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

/**
 * Created by Michael Ruppen on 28.06.2024
 */
@Xml(name = "Attribute")
data class AttributeDto(
    @Element(name = "UserText")
    val userText: NameDto,
    @PropertyElement(name = "Code")
    val code: String
)
