package ch.opentransportdata.ojp

import ch.opentransportdata.ojp.data.dto.response.PlaceResultDto
import ch.opentransportdata.ojp.di.context.OjpKoinContext
import ch.opentransportdata.ojp.domain.model.PlaceTypeRestriction
import ch.opentransportdata.ojp.domain.model.Result
import ch.opentransportdata.ojp.domain.usecase.Initializer
import ch.opentransportdata.ojp.domain.usecase.RequestLocationsFromCoordinates
import ch.opentransportdata.ojp.domain.usecase.RequestLocationsFromSearchTerm
import timber.log.Timber

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

    suspend fun requestLocationsFromCoordinates(
        longitude: Double,
        latitude: Double,
        restrictions: List<PlaceTypeRestriction> = emptyList()
    ): Result<List<PlaceResultDto>> {
        return OjpKoinContext.koinApp.koin.get<RequestLocationsFromCoordinates>().invoke(longitude, latitude, restrictions)
    }

    suspend fun requestLocationsFromSearchTerm(
        term: String,
        restrictions: List<PlaceTypeRestriction>
    ): Result<List<PlaceResultDto>> {
        return OjpKoinContext.koinApp.koin.get<RequestLocationsFromSearchTerm>().invoke(term, restrictions)
    }

}