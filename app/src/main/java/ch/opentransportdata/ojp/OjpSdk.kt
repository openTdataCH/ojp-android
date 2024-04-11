package ch.opentransportdata.ojp

import ch.opentransportdata.ojp.data.dto.response.PlaceResultDto
import ch.opentransportdata.ojp.di.context.OjpKoinContext
import ch.opentransportdata.ojp.domain.model.Response
import ch.opentransportdata.ojp.domain.usecase.Initializer
import ch.opentransportdata.ojp.domain.usecase.RequestLocationsFromCoordinates
import ch.opentransportdata.ojp.domain.usecase.RequestLocationsFromSearchTerm

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
        OjpKoinContext.koin.get<Initializer>().init(baseUrl, endpoint, requesterReference, httpHeaders)
    }

    suspend fun requestLocationsFromCoordinates(
        longitude: Double,
        latitude: Double,
        onlyStation: Boolean
    ): Response<List<PlaceResultDto>> {
        return OjpKoinContext.koin.get<RequestLocationsFromCoordinates>().invoke(longitude, latitude, onlyStation)
    }

    suspend fun requestLocationsFromSearchTerm(term: String, onlyStation: Boolean): Response<List<PlaceResultDto>> {
        return OjpKoinContext.koin.get<RequestLocationsFromSearchTerm>().invoke(term, onlyStation)
    }

}