package ch.opentransportdata.ojp.data.dto.request.lir

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

/**
 * Created by Michael Ruppen on 08.04.2024
 */
@Xml(name = "InitialInput")
data class InitialInputDto(
    @Element(name = "GeoRestriction")
    val geoRestriction: GeoRestrictionDto? = null,

    @PropertyElement(name = "Name")
    val name: String? = null
)