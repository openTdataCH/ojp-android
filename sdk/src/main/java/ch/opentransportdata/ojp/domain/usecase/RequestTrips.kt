package ch.opentransportdata.ojp.domain.usecase

import ch.opentransportdata.ojp.data.dto.request.tir.TripParamsDto
import ch.opentransportdata.ojp.data.dto.response.PlaceResultDto
import ch.opentransportdata.ojp.domain.repository.OjpRepository
import java.time.Instant

/**
 * Created by Michael Ruppen on 27.06.2024
 */
internal class RequestTrips(
    private val ojpRepository: OjpRepository
) {

    suspend operator fun invoke(
        origin: PlaceResultDto,
        destination: PlaceResultDto,
        via: PlaceResultDto? = null,
        time: Instant,
        params: TripParamsDto?
    ) {
        ojpRepository.requestTrips(origin, destination, via, time, params)
    }
}
