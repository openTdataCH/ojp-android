package ch.opentransportdata.ojp.di

import ch.opentransportdata.ojp.BuildConfig
import ch.opentransportdata.ojp.data.dto.converter.*
import ch.opentransportdata.ojp.data.remote.OjpService
import ch.opentransportdata.ojp.di.interceptor.TokenInterceptor
import ch.opentransportdata.ojp.domain.model.*
import ch.opentransportdata.ojp.domain.usecase.Initializer
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.converter.htmlescape.HtmlEscapeStringConverter
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.factoryOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import java.time.Duration
import java.util.concurrent.TimeUnit

/**
 * Created by Michael Ruppen on 08.04.2024
 */
internal val networkModule = module {
    single { provideLoggingInterceptor() }
    single(named("ojpHttpClient")) { provideOkHttpClient(get(), get()) }
    single(named("ojpRetrofit")) { provideRetrofit(get(named("ojpHttpClient")), get(), get()) }
    single { provideTikXml(get()) }
    single<TokenInterceptor> { TokenInterceptor(get()) }
    single { provideOjpService(get(named("ojpRetrofit"))) }
    factoryOf(::LocalDateTimeTypeConverter)
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

internal fun provideRetrofit(ojpHttpClient: OkHttpClient, tikXml: TikXml, initializer: Initializer): Retrofit {
    return Retrofit.Builder()
        .baseUrl(initializer.baseUrl)
        .client(ojpHttpClient)
        .addConverterFactory(TikXmlConverterFactory.create(tikXml))
        .build()
}

internal fun provideTikXml(initializer: Initializer): TikXml {
    return TikXml.Builder()
        .addTypeConverter(java.time.LocalDateTime::class.java, LocalDateTimeTypeConverter(initializer))
        .addTypeConverter(Duration::class.java, DurationTypeConverter())
        .addTypeConverter(String::class.java, HtmlEscapeStringConverter())
        .addTypeConverter(PtMode::class.java, PtModeTypeConverter())
        .addTypeConverter(PlaceTypeRestriction::class.java, PlaceTypeRestrictionConverter())
        .addTypeConverter(TransferType::class.java, TransferTypeConverter())
        .addTypeConverter(ConventionalModesOfOperation::class.java, ConventionalModesOfOperationConverter())
        .addTypeConverter(RealtimeData::class.java, RealTimeDataConverter())
        .exceptionOnUnreadXml(false)
        .build()
}

internal fun provideOjpService(retrofit: Retrofit): OjpService {
    return retrofit.create(OjpService::class.java)
}