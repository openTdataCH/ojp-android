package ch.opentransportdata.ojp.domain.usecase

import ch.opentransportdata.ojp.BuildConfig
import java.time.ZoneId

/**
 * Created by Michael Ruppen on 08.04.2024
 */
internal class Initializer {

    lateinit var baseUrl: String
    lateinit var endpoint: String
    lateinit var requesterReference: String
    lateinit var httpHeaders: HashMap<String, String>
    lateinit var defaultTimeZone: ZoneId

    fun init(
        baseUrl: String,
        endpoint: String,
        requesterReference: String,
        httpHeaders: HashMap<String, String>,
        defaultTimeZone: ZoneId
    ) {
        this.baseUrl = baseUrl
        this.endpoint = endpoint
        this.requesterReference = requesterReference + "_" + ANDROID_SDK + "_" + BuildConfig.VERSION_NAME
        this.httpHeaders = httpHeaders
        this.defaultTimeZone = defaultTimeZone
    }

    companion object {
        private const val ANDROID_SDK = "ANDROID_SDK"
    }
}