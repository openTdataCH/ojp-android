package ch.opentransportdata.ojp.domain.usecase

import ch.opentransportdata.ojp.data.dto.request.tir.PlaceReferenceDto
import ch.opentransportdata.ojp.data.dto.response.delivery.TripDeliveryDto
import ch.opentransportdata.ojp.data.dto.response.tir.TripResultDto
import ch.opentransportdata.ojp.data.dto.response.tir.trips.TripDto
import ch.opentransportdata.ojp.domain.model.LanguageCode
import ch.opentransportdata.ojp.domain.model.Result
import ch.opentransportdata.ojp.domain.model.TripParams
import ch.opentransportdata.ojp.domain.repository.OjpRepository

/**
 * Created by Michael Ruppen on 10.10.2024
 */
internal class UpdateTrip(
    private val ojpRepository: OjpRepository
) {

    //todo: write tests for the useCase!
    suspend operator fun invoke(
        languageCode: LanguageCode,
        origin: PlaceReferenceDto,
        destination: PlaceReferenceDto,
        via: PlaceReferenceDto?,
        params: TripParams?,
        trip: TripDto,
    ): Result<TripDeliveryDto> {

        return when (val response = ojpRepository.requestTrips(
            languageCode = languageCode,
            origin = origin,
            destination = destination,
            via = via,
            time = trip.startTime.minusMinutes(5),
            isSearchForDepartureTime = true,
            params = params,
        )) {
            is Result.Success -> {
                var updatedTrip: TripDto?
                val trips = response.data.tripResults?.mapNotNull { it.trip as? TripDto }
                updatedTrip = trips?.find { it.hashCode() == trip.hashCode() }

                if (updatedTrip == null) {
                    updatedTrip = trips?.find {
                        it.firstTimedLeg.legBoard.serviceDeparture.timetabledTime == trip.firstTimedLeg.legBoard.serviceDeparture.timetabledTime
                                && it.firstTimedLeg.legAlight.serviceArrival.timetabledTime == trip.firstTimedLeg.legAlight.serviceArrival.timetabledTime
                                && it.lastTimedLeg.legBoard.serviceDeparture.timetabledTime == trip.lastTimedLeg.legBoard.serviceDeparture.timetabledTime
                                && it.lastTimedLeg.legAlight.serviceArrival.timetabledTime == trip.lastTimedLeg.legAlight.serviceArrival.timetabledTime
                    }
                }

                val tripResults = when (updatedTrip == null) {
                    true -> listOf()
                    else -> {
                        listOf(
                            TripResultDto(
                                id = updatedTrip.id,
                                trip = updatedTrip,
                                isAlternativeOption = null
                            )
                        )
                    }
                }

                val result = response.data.copy(tripResults = tripResults)
                Result.Success(result)
            }

            is Result.Error -> Result.Error(response.error)
        }
    }
}