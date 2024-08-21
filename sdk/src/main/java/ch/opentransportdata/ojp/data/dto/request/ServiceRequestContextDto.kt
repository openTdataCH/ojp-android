package ch.opentransportdata.ojp.data.dto.request

import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

/**
 * Created by Michael Ruppen on 21.08.2024
 */
@Xml(name = "siri:ServiceRequestContext")
data class ServiceRequestContextDto(
    @PropertyElement(name = "siri:Language")
    val language: String,
)
