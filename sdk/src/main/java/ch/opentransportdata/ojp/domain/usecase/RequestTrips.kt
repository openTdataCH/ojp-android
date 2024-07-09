package ch.opentransportdata.ojp.domain.usecase

import ch.opentransportdata.ojp.data.dto.request.tir.TripParamsDto
import ch.opentransportdata.ojp.data.dto.response.PlaceResultDto
import ch.opentransportdata.ojp.data.dto.response.delivery.TripDeliveryDto
import ch.opentransportdata.ojp.domain.model.Result
import ch.opentransportdata.ojp.domain.repository.OjpRepository
import java.time.LocalDateTime

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
        time: LocalDateTime,
        isSearchForDepartureTime: Boolean,
        params: TripParamsDto?
    ): Result<TripDeliveryDto> {
        return when (val response =
            ojpRepository.requestTrips(
                origin = origin,
                destination = destination,
                via = via,
                time = time,
                isSearchForDepartureTime = isSearchForDepartureTime,
                params = params
            )
        ) {
            is Result.Success -> Result.Success(response.data)
            is Result.Error -> Result.Error(response.error)
        }
    }
}
