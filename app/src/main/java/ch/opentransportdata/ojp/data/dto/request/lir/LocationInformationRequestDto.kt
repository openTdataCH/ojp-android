package ch.opentransportdata.ojp.data.dto.request.lir

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml


/**
 * Created by Michael Ruppen on 08.04.2024
 */
@Xml(name = "OJPLocationInformationRequest")
data class LocationInformationRequestDto(
    @PropertyElement(name = "siri:RequestTimestamp")
    val requestTimestamp: String,

    @Element(name = "InitialInput")
    val initialInput: InitialInputDto,

    @Element(name = "Restrictions")
    val restrictions: RestrictionsDto
)