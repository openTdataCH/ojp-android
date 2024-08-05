package ch.opentransportdata.ojp.data.dto.response.tir

import ch.opentransportdata.ojp.data.dto.response.tir.leg.AbstractLegType
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml
import java.time.Duration

/**
 * Created by Michael Ruppen on 28.06.2024
 */
@Xml(name = "Leg")
data class LegDto(
    @PropertyElement(name = "Id")
    val id: String,
    @PropertyElement(name = "Duration")
    val duration: Duration?,
    @Element
    val legType: AbstractLegType
)
