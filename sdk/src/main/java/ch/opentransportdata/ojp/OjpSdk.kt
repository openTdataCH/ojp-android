package ch.opentransportdata.ojp

import ch.opentransportdata.ojp.data.dto.request.tir.PlaceReferenceDto
import ch.opentransportdata.ojp.data.dto.response.PlaceResultDto
import ch.opentransportdata.ojp.data.dto.response.delivery.TripDeliveryDto
import ch.opentransportdata.ojp.di.context.OjpKoinContext
import ch.opentransportdata.ojp.domain.model.LanguageCode
import ch.opentransportdata.ojp.domain.model.LocationInformationParams
import ch.opentransportdata.ojp.domain.model.Result
import ch.opentransportdata.ojp.domain.model.TripParams
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
     * @param languageCode The [LanguageCode] for the desired results, default is [LanguageCode.DE]
     * @param longitude The longitude of the geographical point
     * @param latitude The latitude of the geographical point
     * @param restrictions Restrictions that should be used for results
     * @return List of [PlaceResultDto] sorted by the nearest point
     */
    suspend fun requestLocationsFromCoordinates(
        languageCode: LanguageCode = LanguageCode.DE,
        longitude: Double,
        latitude: Double,
        restrictions: LocationInformationParams
    ): Result<List<PlaceResultDto>> {
        return OjpKoinContext.koinApp.koin.get<RequestLocationsFromCoordinates>()
            .invoke(languageCode, longitude, latitude, restrictions)
    }

    /**
     * Request a list of Place Results based on the given search term
     *
     * @param languageCode The [LanguageCode] for the desired results, default is [LanguageCode.DE]
     * @param term The given search term
     * @param restrictions Restrictions that should be used for results
     * @return List of [PlaceResultDto] that contains the search term
     */
    suspend fun requestLocationsFromSearchTerm(
        languageCode: LanguageCode = LanguageCode.DE,
        term: String,
        restrictions: LocationInformationParams
    ): Result<List<PlaceResultDto>> {
        return OjpKoinContext.koinApp.koin.get<RequestLocationsFromSearchTerm>().invoke(languageCode, term, restrictions)
    }

    /**
     * Request a list of trips
     *
     * @param languageCode The [LanguageCode] for the desired results, default is [LanguageCode.DE]
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
        languageCode: LanguageCode = LanguageCode.DE,
        origin: PlaceReferenceDto,
        destination: PlaceReferenceDto,
        via: PlaceReferenceDto? = null,
        time: LocalDateTime,
        isSearchForDepartureTime: Boolean = true,
        params: TripParams
    ): Result<TripDeliveryDto> {
        OjpKoinContext.koinApp.koin.get<RequestTrips>().reset()
        return OjpKoinContext.koinApp.koin.get<RequestTrips>().invoke(
            languageCode = languageCode,
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
     * @param numberOfResults The number of results that should be loaded before the current first trip
     *
     * @return [TripDeliveryDto] with previous trips that are not already returned
     */
    suspend fun requestPreviousTrips(numberOfResults: Int = 5): Result<TripDeliveryDto> {
        return OjpKoinContext.koinApp.koin.get<RequestTrips>().loadPrevious(numberOfResults)
    }

    /**
     * Load further trips according to your last trip item of the tripResult list.
     * Need to do a [requestTrips] call first!
     *
     * @param numberOfResults The number of results that should be loaded after the current first trip
     *
     * @return [TripDeliveryDto] with next trips that are not already returned
     */
    suspend fun requestNextTrips(numberOfResults: Int = 5): Result<TripDeliveryDto> {
        return OjpKoinContext.koinApp.koin.get<RequestTrips>().loadNext(numberOfResults)
    }

}