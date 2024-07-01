package ch.opentransportdata.ojp.data.dto.response

import ch.opentransportdata.ojp.domain.model.PtMode
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

/**
 * Created by Michael Ruppen on 08.04.2024
 */
@Xml(name = "Mode")
data class ModeDto(
    @PropertyElement(name = "PtMode")
    val ptMode: PtMode,
    @Element(name = "Name")
    val name: NameDto?
)