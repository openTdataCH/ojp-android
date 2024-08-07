package ch.opentransportdata.ojp.data.dto.response.tir.leg

import ch.opentransportdata.ojp.data.dto.response.NameDto
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

/**
 * Created by Michael Ruppen on 28.06.2024
 */

@Xml(name = "ProductCategory")
data class ProductCategoryDto(
    @Element(name = "Name")
    val name: NameDto?,
    @Element(name = "ShortName")
    val shortName: NameDto?,
    @PropertyElement(name = "ProductCategoryRef")
    val productCategoryRef: String?
)