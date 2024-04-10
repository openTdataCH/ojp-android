package ch.opentransportdata.ojp.domain.usecase

import ch.opentransportdata.ojp.BuildConfig
import ch.opentransportdata.ojp.domain.model.SdkConfig

/**
 * Created by Michael Ruppen on 08.04.2024
 */
class Initializer(private val sdkConfig: SdkConfig) {

    lateinit var baseUrl: String
    lateinit var endpoint: String
    lateinit var requesterReference: String
    lateinit var httpHeaders: HashMap<String, String>

    fun init() {
        baseUrl = sdkConfig.baseUrl
        endpoint = sdkConfig.endpoint
        requesterReference = sdkConfig.requesterReference + "_" + ANDROID_SDK + "_" + BuildConfig.VERSION_NAME
        httpHeaders = sdkConfig.httpHeaders
    }

    companion object {
        private const val ANDROID_SDK = "ANDROID_SDK"
    }
}