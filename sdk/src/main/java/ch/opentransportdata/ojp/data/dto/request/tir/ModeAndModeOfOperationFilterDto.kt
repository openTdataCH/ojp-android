package ch.opentransportdata.ojp.data.dto.request.tir

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.TextContent
import com.tickaroo.tikxml.annotation.Xml

/**
 * Created by Michael Ruppen on 31.07.2024
 */
@Xml(name = "ModeAndModeOfOperationFilter")
internal data class ModeAndModeOfOperationFilterDto(
    @Element(name = "PtMode")
    val ptMode: List<PtModeType>?,
    @PropertyElement(name = "Exclude")
    val exclude: Boolean?
)


//workaround for tikXml: https://github.com/Tickaroo/tikxml/issues/46
@Xml
internal data class PtModeType(
    @TextContent
    val type: String
)
