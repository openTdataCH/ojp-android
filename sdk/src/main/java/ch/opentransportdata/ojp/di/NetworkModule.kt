package ch.opentransportdata.ojp.di

import ch.opentransportdata.ojp.BuildConfig
import ch.opentransportdata.ojp.data.dto.converter.FareClassSerializer
import ch.opentransportdata.ojp.data.dto.converter.XmlUtilConverterFactory
import ch.opentransportdata.ojp.data.remote.OjpService
import ch.opentransportdata.ojp.di.interceptor.TokenInterceptor
import ch.opentransportdata.ojp.domain.model.FareClass
import ch.opentransportdata.ojp.domain.usecase.Initializer
import kotlinx.serialization.modules.SerializersModule
import nl.adaptivity.xmlutil.ExperimentalXmlUtilApi
import nl.adaptivity.xmlutil.serialization.XML
import nl.adaptivity.xmlutil.serialization.XmlConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

/**
 * Created by Michael Ruppen on 08.04.2024
 */
internal val networkModule = module {
    single { provideXml() }
    single { provideLoggingInterceptor() }
    single(named("ojpHttpClient")) { provideOkHttpClient(get(), get()) }
    single(named("ojpRetrofit")) { provideRetrofit(get(named("ojpHttpClient")), get(), get()) }
    single<TokenInterceptor> { TokenInterceptor(get()) }
    single { provideOjpService(get(named("ojpRetrofit"))) }
}

internal fun provideLoggingInterceptor(): HttpLoggingInterceptor {
    val loggingInterceptor = HttpLoggingInterceptor()

    loggingInterceptor.level = when (BuildConfig.DEBUG) {
        true -> HttpLoggingInterceptor.Level.BODY
        else -> HttpLoggingInterceptor.Level.NONE
    }

    return loggingInterceptor
}

internal fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor, tokenInterceptor: TokenInterceptor): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(tokenInterceptor)
        .readTimeout(15000, TimeUnit.MILLISECONDS)
        .build()
}

internal fun provideRetrofit(
    ojpHttpClient: OkHttpClient,
    initializer: Initializer,
    xml: XML
): Retrofit {
    return Retrofit.Builder()
        .baseUrl(initializer.baseUrl)
        .client(ojpHttpClient)
        .addConverterFactory(XmlUtilConverterFactory.create(xml))
        .build()
}

@OptIn(ExperimentalXmlUtilApi::class)
internal fun provideXml(): XML = XML(
    serializersModule = SerializersModule {
        contextual(FareClass::class, FareClassSerializer)
    }
) {
    repairNamespaces = true
    defaultPolicy {
        unknownChildHandler = XmlConfig.IGNORING_UNKNOWN_CHILD_HANDLER
        verifyElementOrder = false
        throwOnRepeatedElement = false
        isStrictBoolean = true
        isStrictAttributeNames = true
    }
}

internal fun provideOjpService(retrofit: Retrofit): OjpService {
    return retrofit.create(OjpService::class.java)
}