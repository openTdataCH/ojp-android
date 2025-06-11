package ch.opentransportdata.ojp.data.dto.response.tir.trips

import android.os.Parcelable
import ch.opentransportdata.ojp.data.dto.response.PlacesDto

/**
 * Created by Michael Ruppen on 01.07.2024
 */
abstract class AbstractTripDto : Parcelable {
    abstract val id: String
}

fun AbstractTripDto.minimalCopy(placesDto: PlacesDto? = null): AbstractTripDto {
    return when (this) {
        is TripDto -> this.minimalCopy(places = placesDto)
        else -> this
    }
}