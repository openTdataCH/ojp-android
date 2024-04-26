package ch.opentransportdata.ojp.data.dto.request.lir

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.TextContent
import com.tickaroo.tikxml.annotation.Xml


/**
 * Created by Michael Ruppen on 08.04.2024
 */
@Xml(name = "Restrictions")
internal data class RestrictionsDto(
    @Element(name = "Type")
    val types: List<RestrictionType>,

    @PropertyElement(name = "NumberOfResults")
    val numberOfResults: Int,

    @PropertyElement(name = "IncludePtModes")
    val ptModeIncluded: Boolean
)

//workaround for tikXml: https://github.com/Tickaroo/tikxml/issues/46
@Xml
internal data class RestrictionType(
    @TextContent
    val type: String
)
