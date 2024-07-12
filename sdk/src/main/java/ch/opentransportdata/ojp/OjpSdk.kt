package ch.opentransportdata.ojp

import ch.opentransportdata.ojp.data.dto.request.tir.TripParamsDto
import ch.opentransportdata.ojp.data.dto.response.PlaceResultDto
import ch.opentransportdata.ojp.data.dto.response.delivery.TripDeliveryDto
import ch.opentransportdata.ojp.di.context.OjpKoinContext
import ch.opentransportdata.ojp.domain.model.LocationInformationParams
import ch.opentransportdata.ojp.domain.model.Result
import ch.opentransportdata.ojp.domain.usecase.Initializer
import ch.opentransportdata.ojp.domain.usecase.RequestLocationsFromCoordinates
import ch.opentransportdata.ojp.domain.usecase.RequestLocationsFromSearchTerm
import ch.opentransportdata.ojp.domain.usecase.RequestTrips
import timber.log.Timber
import java.time.LocalDateTime

/**
 * Created by Michael Ruppen on 08.04.2024
 *
 * @param baseUrl The url where the SDK shall point to do the OJP requests ex: https://api.opentransportdata.swiss/
 * @param endpoint The specific endpoint on the baseUrl to request ex: ojp20 => https://api.opentransportdata.swiss/ojp20
 * @param requesterReference The reference for requests to help tracking on the OJP backend
 * @param httpHeaders Define custom http headers ex. key: "Authorization" value: "Bearer xyz"
 */
class OjpSdk(
    baseUrl: String,
    endpoint: String,
    requesterReference: String,
    httpHeaders: HashMap<String, String> = hashMapOf()
) {

    init {
        Timber.i("Initialize SDK")
        OjpKoinContext.koinApp.koin.get<Initializer>().init(baseUrl, endpoint, requesterReference, httpHeaders)
    }

    /**
     * Request a list of Place Results based on the given geographical point
     *
     *
     * @param longitude The longitude of the geographical point
     * @param latitude The latitude of the geographical point
     * @param restrictions Restrictions that should be used for results
     * @return List of [PlaceResultDto] sorted by the nearest point
     */
    suspend fun requestLocationsFromCoordinates(
        longitude: Double,
        latitude: Double,
        restrictions: LocationInformationParams
    ): Result<List<PlaceResultDto>> {
        return OjpKoinContext.koinApp.koin.get<RequestLocationsFromCoordinates>().invoke(longitude, latitude, restrictions)
    }

    /**
     * Request a list of Place Results based on the given search term
     *
     * @param term The given search term
     * @param restrictions Restrictions that should be used for results
     * @return List of [PlaceResultDto] that contains the search term
     */
    suspend fun requestLocationsFromSearchTerm(
        term: String,
        restrictions: LocationInformationParams
    ): Result<List<PlaceResultDto>> {
        return OjpKoinContext.koinApp.koin.get<RequestLocationsFromSearchTerm>().invoke(term, restrictions)
    }

    /**
     * Request a list of trips
     *
     * @param origin The origin where the trip starts
     * @param destination The destination where the trip ends
     * @param via The via station which the trip should cover
     * @param time The time the trip should start/end
     * @param isSearchForDepartureTime weather to search for trips that arrive at [time] or leave at [time]. Set to true if searching for trips that leave at [time].
     * @param params The params to get further information on each trip
     *
     * @return [TripDeliveryDto] object with related trip information
     */
    suspend fun requestTrips(
        origin: PlaceResultDto,
        destination: PlaceResultDto,
        via: PlaceResultDto? = null,
        time: LocalDateTime,
        isSearchForDepartureTime: Boolean = true,
        params: TripParamsDto?
    ): Result<TripDeliveryDto> {
        return OjpKoinContext.koinApp.koin.get<RequestTrips>()
            .invoke(
                origin = origin,
                destination = destination,
                via = via,
                time = time,
                isSearchForDepartureTime = isSearchForDepartureTime,
                params = params
            )
    }

    /**
     * Load previous trips according to your first trip item of the tripResult list.
     * Need to do a [requestTrips] call first!
     *
     * @return [TripDeliveryDto] with previous trips that are not already returned
     */
    suspend fun requestPreviousTrips(): Result<TripDeliveryDto> {
        return OjpKoinContext.koinApp.koin.get<RequestTrips>().loadPrevious()
    }

    /**
     * Load further trips according to your last trip item of the tripResult list.
     * Need to do a [requestTrips] call first!
     *
     * @return [TripDeliveryDto] with next trips that are not already returned
     */
    suspend fun requestNextTrips(): Result<TripDeliveryDto> {
        return OjpKoinContext.koinApp.koin.get<RequestTrips>().loadNext()
    }

    /**
     * Reset the current tripRequest state so no loadNext/Previous works anymore and a clean trip search can be done.
     * Do not have to be called for initial [requestTrips] call
     */
    fun resetTripState() {
        OjpKoinContext.koinApp.koin.get<RequestTrips>().reset()
    }

}