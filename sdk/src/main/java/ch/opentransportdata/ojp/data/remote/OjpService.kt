package ch.opentransportdata.ojp.data.remote

import ch.opentransportdata.ojp.data.dto.OjpDto
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Url

/**
 * Created by Michael Ruppen on 08.04.2024
 */
internal interface OjpService {

    @POST
    suspend fun locationInformationRequest(
        @Url url: String,
        @Body ojpDto: OjpDto
    ): OjpDto
}