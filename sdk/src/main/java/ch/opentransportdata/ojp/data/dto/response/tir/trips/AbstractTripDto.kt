package ch.opentransportdata.ojp.data.dto.response.tir.trips

/**
 * Created by Michael Ruppen on 01.07.2024
 */
abstract class AbstractTripDto {
    abstract val id: String
}

fun AbstractTripDto.minimalCopy(): AbstractTripDto {
    return when (this) {
        is TripDto -> this.minimalCopy()
        else -> this
    }
}