package ch.opentransportdata.ojp.domain.usecase

import ch.opentransportdata.ojp.BuildConfig
import ch.opentransportdata.ojp.domain.model.SdkConfig

/**
 * Created by Michael Ruppen on 08.04.2024
 */
class Initializer(private val sdkConfig: SdkConfig) {

    lateinit var baseUrl: String
    lateinit var endpoint: String
    lateinit var accessToken: String
    lateinit var requestReference: String


    fun init() {
        baseUrl = sdkConfig.baseUrl
        endpoint = sdkConfig.endpoint
        accessToken = sdkConfig.accessToken
        requestReference = sdkConfig.requestReference + "_" + ANDROID_SDK + "_" + BuildConfig.VERSION_NAME
    }

    companion object {
        private const val ANDROID_SDK = "ANDROID_SDK"
    }
}