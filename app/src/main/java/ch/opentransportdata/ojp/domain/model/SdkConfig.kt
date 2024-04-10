package ch.opentransportdata.ojp.domain.model

/**
 * Created by Michael Ruppen on 08.04.2024
 *
 * @param baseUrl The url where the SDK shall point to do the OJP requests ex: https://api.opentransportdata.swiss/
 * @param endpoint The specific endpoint on the baseUrl to request ex: ojp20 => https://api.opentransportdata.swiss/ojp20
 * @param accessToken The token to access the baseUrl (for "Authorization Bearer")
 * @param requestorReference The reference for requests to help tracking on the OJP backend
 *
 */
data class SdkConfig(
    val baseUrl: String,
    val endpoint: String,
    val accessToken: String,
    val requestorReference: String
)
