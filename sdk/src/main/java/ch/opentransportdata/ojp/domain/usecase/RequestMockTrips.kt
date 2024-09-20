package ch.opentransportdata.ojp.domain.usecase

import ch.opentransportdata.ojp.data.dto.response.delivery.TripDeliveryDto
import ch.opentransportdata.ojp.domain.model.Result
import ch.opentransportdata.ojp.domain.repository.OjpRepository
import java.io.InputStream

/**
 * Created by Michael Ruppen on 19.09.2024
 */
internal class RequestMockTrips(
    private val ojpRepository: OjpRepository
) {

    suspend operator fun invoke(stream: InputStream): Result<TripDeliveryDto> {
        return when (val response = ojpRepository.requestMockTrips(stream)) {
            is Result.Success -> Result.Success(response.data)
            is Result.Error -> Result.Error(response.error)
        }
    }

}