package ch.opentransportdata.ojp.data.remote.location

import ch.opentransportdata.ojp.data.dto.OjpDto
import ch.opentransportdata.ojp.domain.model.LanguageCode
import ch.opentransportdata.ojp.domain.model.LocationInformationParams

/**
 * Created by Michael Ruppen on 08.04.2024
 */
internal interface RemoteLocationInformationDataSource {

    suspend fun searchLocationBySearchTerm(
        languageCode: LanguageCode,
        term: String,
        restrictions: LocationInformationParams
    ): OjpDto

    suspend fun searchLocationByCoordinates(
        languageCode: LanguageCode,
        longitude: Double,
        latitude: Double,
        restrictions: LocationInformationParams
    ): OjpDto
}