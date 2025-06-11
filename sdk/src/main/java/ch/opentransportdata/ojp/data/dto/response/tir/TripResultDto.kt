package ch.opentransportdata.ojp.data.dto.response.tir

import android.os.Parcelable
import ch.opentransportdata.ojp.data.dto.response.tir.trips.TripDto
import ch.opentransportdata.ojp.data.dto.response.tir.trips.minimalCopy
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml
import kotlinx.parcelize.Parcelize

/**
 * Created by Michael Ruppen on 28.06.2024
 */
@Xml(name = "TripResult")
@Parcelize
data class TripResultDto(
    @PropertyElement(name = "Id")
    val id: String,
    @Element
    val trip: TripDto?,
    @PropertyElement(name = "IsAlternativeOption")
    val isAlternativeOption: Boolean?
) : Parcelable

val TripResultDto.minimalTripResult: TripResultDto
    get() = TripResultDto(
        trip = trip?.minimalCopy(places = null),
        id = id,
        isAlternativeOption = null
    )