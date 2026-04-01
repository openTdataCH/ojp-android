package ch.opentransportdata.ojp.domain.usecase

import ch.opentransportdata.ojp.data.dto.response.delivery.TripInfoDeliveryDto
import ch.opentransportdata.ojp.domain.model.LanguageCode
import ch.opentransportdata.ojp.domain.model.Result
import ch.opentransportdata.ojp.domain.model.TripInfoParam
import ch.opentransportdata.ojp.domain.model.error.OjpError
import ch.opentransportdata.ojp.domain.repository.OjpRepository
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.isActive
import java.util.concurrent.CancellationException

/**
 * Created by Deniz Kalem on 26.03.2026
 */
internal class RequestTripInfo(
    private val ojpRepository: OjpRepository
) {
    suspend operator fun invoke(
        languageCode: LanguageCode,
        journeyRef: String,
        operatingDayRef: String,
        params: TripInfoParam?
    ): Result<TripInfoDeliveryDto> {
        if (!currentCoroutineContext().isActive) return Result.Error(OjpError.RequestCancelled(CancellationException()))

        return when (val response = ojpRepository.requestTripInfo(
            languageCode = languageCode,
            journeyRef = journeyRef,
            operatingDayRef = operatingDayRef,
            params = params
        )) {
            is Result.Success -> Result.Success(response.data)
            is Result.Error -> Result.Error(response.error)
        }
    }
}