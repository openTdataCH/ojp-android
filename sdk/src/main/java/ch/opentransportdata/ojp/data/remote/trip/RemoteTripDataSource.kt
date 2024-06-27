package ch.opentransportdata.ojp.data.remote.trip

import ch.opentransportdata.ojp.data.dto.OjpDto
import ch.opentransportdata.ojp.data.dto.request.tir.TripParamsDto
import ch.opentransportdata.ojp.data.dto.response.PlaceResultDto
import org.joda.time.LocalDateTime

/**
 * Created by Michael Ruppen on 27.06.2024
 */
internal interface RemoteTripDataSource {
    suspend fun requestTrips(
        origin: PlaceResultDto,
        destination: PlaceResultDto,
        via: PlaceResultDto? = null,
        time: LocalDateTime,
        params: TripParamsDto?
    ): OjpDto
}