package ch.opentransportdata.ojp.data.dto.response.tir.leg

import android.os.Parcelable
import ch.opentransportdata.ojp.domain.model.FareClass
import ch.opentransportdata.ojp.domain.model.OccupancyLevel
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml
import kotlinx.parcelize.Parcelize

/**
 * Created by Deniz Kalem on 25.06.2025
 */
@Parcelize
@Xml(name = "siri:ExpectedDepartureOccupancy")
data class ExpectedDepartureOccupancyDto(
    @PropertyElement(name = "siri:FareClass")
    val fareClass: FareClass,
    @PropertyElement(name = "siri:OccupancyLevel")
    val occupancyLevel: OccupancyLevel
) : Parcelable