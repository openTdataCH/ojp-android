package ch.opentransportdata.ojp.data.dto.response.tir

import ch.opentransportdata.ojp.data.dto.response.tir.leg.AbstractLegType
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

/**
 * Created by Michael Ruppen on 28.06.2024
 */
//todo: add custom adapter, use generated one as base
@Xml(name = "Leg")
data class LegDto(
    @PropertyElement(name = "Id")
    val id: String,
    @PropertyElement(name = "Duration")
    val duration: String,
    @Element
    val legType: AbstractLegType
)
