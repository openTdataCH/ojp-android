package ch.opentransportdata.ojp.data.dto.response.tir.situations

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Xml

/**
 * Created by Michael Ruppen on 28.06.2024
 */
@Xml(name = "siri:Consequences")
data class ConsequencesDto(
    @Element(name = "siri:Consequence")
    val consequence: ConsequenceDto
)
