package ch.opentransportdata.ojp.data.remote.trip

import ch.opentransportdata.ojp.data.dto.OjpDto
import ch.opentransportdata.ojp.data.dto.request.tir.PlaceReferenceDto
import ch.opentransportdata.ojp.data.dto.request.tir.TripParamsDto
import java.time.LocalDateTime

/**
 * Created by Michael Ruppen on 27.06.2024
 */
internal interface RemoteTripDataSource {
    suspend fun requestTrips(
        origin: PlaceReferenceDto,
        destination: PlaceReferenceDto,
        via: PlaceReferenceDto? = null,
        time: LocalDateTime,
        isSearchForDepartureTime: Boolean,
        params: TripParamsDto?
    ): OjpDto
}