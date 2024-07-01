package ch.opentransportdata.ojp.data.dto.response.tir.trips

import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

/**
 * Created by Michael Ruppen on 01.07.2024
 */
@Xml(name = "TripSummary")
data class TripSummaryDto(
    @PropertyElement(name = "TripId")
    override val id: String
) : AbstractTripDto()
