package ch.opentransportdata.ojp.data.dto.response

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Xml

/**
 * Created by Michael Ruppen on 08.04.2024
 */
@Xml(name = "OJPLocationInformationDelivery")
data class LocationInformationResponseDto(
    @Element(name = "PlaceResult")
    val placeResults: List<PlaceResultDto>?,
)
