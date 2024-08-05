package ch.opentransportdata.ojp.data.dto.response.tir.situations

import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

/**
 * Created by Michael Ruppen on 28.06.2024
 */
@Xml(name = "siri:Source")
data class SourceDto(
    @PropertyElement(name = "siri:SourceType")
    val sourceType: String? //todo: check siri doc for info: https://laidig.github.io/siri-20-java/doc/
)