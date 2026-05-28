package ch.opentransportdata.ojp.domain.usecase

import ch.opentransportdata.ojp.data.dto.request.ser.LocationDto
import ch.opentransportdata.ojp.data.dto.response.delivery.StopEventDeliveryDto
import ch.opentransportdata.ojp.domain.model.LanguageCode
import ch.opentransportdata.ojp.domain.model.Result
import ch.opentransportdata.ojp.domain.model.StopEventParam
import ch.opentransportdata.ojp.domain.model.error.OjpError
import ch.opentransportdata.ojp.domain.repository.OjpRepository
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.isActive
import java.util.concurrent.CancellationException

/**
 * Created by Deniz Kalem on 20.05.2026
 */
internal class RequestStopEvent(
    private val ojpRepository: OjpRepository
) {
    suspend operator fun invoke(
        languageCode: LanguageCode,
        location: LocationDto,
        params: StopEventParam?
    ): Result<StopEventDeliveryDto> {
        if (!currentCoroutineContext().isActive) return Result.Error(OjpError.RequestCancelled(CancellationException()))

        return when (val response = ojpRepository.requestStopEvent(
            languageCode = languageCode,
            location = location,
            params = params
        )) {
            is Result.Success -> Result.Success(response.data)
            is Result.Error -> Result.Error(response.error)
        }
    }
}