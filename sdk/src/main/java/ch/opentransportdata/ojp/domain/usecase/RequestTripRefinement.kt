package ch.opentransportdata.ojp.domain.usecase

import ch.opentransportdata.ojp.data.dto.response.delivery.TripRefineDeliveryDto
import ch.opentransportdata.ojp.data.dto.response.tir.TripResultDto
import ch.opentransportdata.ojp.domain.model.LanguageCode
import ch.opentransportdata.ojp.domain.model.Result
import ch.opentransportdata.ojp.domain.model.TripRefineParam
import ch.opentransportdata.ojp.domain.model.error.OjpError
import ch.opentransportdata.ojp.domain.repository.OjpRepository
import kotlinx.coroutines.isActive
import java.util.concurrent.CancellationException
import kotlin.coroutines.coroutineContext

/**
 * Created by Nico Brandenberger on 07.04.2025
 */

internal class RequestTripRefinement(
    private val ojpRepository: OjpRepository
) {
    suspend operator fun invoke(
        languageCode: LanguageCode,
        tripResultDto: TripResultDto,
        params: TripRefineParam
    ): Result<TripRefineDeliveryDto> {
        if (!coroutineContext.isActive) return Result.Error(OjpError.RequestCancelled(CancellationException()))

        return when (val response = ojpRepository.requestTripRefinement(
            languageCode = languageCode,
            tripResultDto = tripResultDto,
            params = params
        )
        ) {
            is Result.Success -> {
                val delivery = response.data
                Result.Success(delivery)
            }

            is Result.Error -> Result.Error(response.error)
        }
    }
}