package ch.opentransportdata.ojp

import ch.opentransportdata.ojp.data.dto.response.PlaceResultDto
import ch.opentransportdata.ojp.di.context.OjpKoinContext
import ch.opentransportdata.ojp.domain.model.Response
import ch.opentransportdata.ojp.domain.model.SdkConfig
import ch.opentransportdata.ojp.domain.usecase.Initializer
import ch.opentransportdata.ojp.domain.usecase.RequestLocationsFromCoordinates
import ch.opentransportdata.ojp.domain.usecase.RequestLocationsFromSearchTerm
import org.koin.core.parameter.parametersOf

/**
 * Created by Michael Ruppen on 08.04.2024
 *
 * Used for communication with the SDK
 */
// TODO Think about structure of the sdk functions and define an api
object OjpSdk {
    fun initializeSDK(sdkConfig: SdkConfig) {
        OjpKoinContext.koin.get<Initializer> { parametersOf(sdkConfig) }.init()
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