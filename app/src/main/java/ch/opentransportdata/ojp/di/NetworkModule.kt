package ch.opentransportdata.ojp.di

import ch.opentransportdata.ojp.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

/**
 * Created by Michael Ruppen on 08.04.2024
 */

fun provideLoggingInterceptor(): HttpLoggingInterceptor {
    val loggingInterceptor = HttpLoggingInterceptor()

    loggingInterceptor.level = when (BuildConfig.DEBUG) {
        true -> HttpLoggingInterceptor.Level.BODY
        else -> HttpLoggingInterceptor.Level.NONE
    }

    return loggingInterceptor
}

fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .readTimeout(15000, TimeUnit.MILLISECONDS)
        .build()
}

fun provideRetrofit(ojpHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .client(ojpHttpClient)
        .build()
}