package ch.opentransportdata.ojp.di.interceptor

import ch.opentransportdata.ojp.domain.usecase.Initializer
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by Michael Ruppen on 08.04.2024
 */
class TokenInterceptor(private val initializer: Initializer) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val headerBuilder = Headers.Builder()
        initializer.httpHeaders.forEach { item ->
            headerBuilder.add(item.key, item.value)
        }
        headerBuilder.add("Content-Type", "application/xml")
        val headers = headerBuilder.build()

        return chain.proceed(
            chain
                .request()
                .newBuilder()
                .headers(headers)
                .method(chain.request().method, chain.request().body)
                .build()
        )
    }
}