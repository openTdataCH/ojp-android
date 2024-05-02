package ch.opentransportdata.ojp.data.dto.response

import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

/**
 * Created by Michael Ruppen on 26.04.2024
 */
@Xml(name = "PrivateCode")
data class PrivateCodeDto(
    @PropertyElement(name = "System")
    val system: String,
    @PropertyElement(name = "Value")
    val value: String,
)
