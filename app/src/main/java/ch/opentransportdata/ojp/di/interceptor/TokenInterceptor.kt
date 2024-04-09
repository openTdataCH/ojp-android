package ch.opentransportdata.ojp.di.interceptor

import ch.opentransportdata.ojp.domain.usecase.Initializer
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by Michael Ruppen on 08.04.2024
 */
internal class TokenInterceptor(private val initializer: Initializer) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(
            chain
                .request()
                .newBuilder()
                .addHeader("Authorization", "Bearer ${initializer.accessToken}")
                .addHeader("Content-Type", "application/xml")
                .method(chain.request().method, chain.request().body)
                .build()
        )
    }
}