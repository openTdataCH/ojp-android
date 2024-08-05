package ch.opentransportdata.ojp.data.dto.request.tir

import ch.opentransportdata.ojp.domain.model.PtMode
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

/**
 * Created by Michael Ruppen on 31.07.2024
 */
@Xml(name = "ModeAndModeOfOperationFilter")
data class ModeAndModeOfOperationFilter(
//    @Element(name = "PtMode")
//    val ptMode: List<PtMode>?, //todo: tikXml cant parse list of enum.. maybe use workaround as in RestrictionDto only for the request, get example of request..
    @PropertyElement(name = "Exclude")
    val exclude: Boolean?
)
