package ch.opentransportdata.ojp.di

import ch.opentransportdata.ojp.BuildConfig
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.converter.htmlescape.HtmlEscapeStringConverter
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

/**
 * Created by Michael Ruppen on 08.04.2024
 */
val networkModule = module {
    single { provideLoggingInterceptor() }
    single(named("ojpHttpClient")) { provideOkHttpClient(get()) }
    single(named("ojpRetrofit")) { provideRetrofit(get(named("ojpHttpClient")), get()) }
    single { provideTikXml() }
}

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

fun provideRetrofit(ojpHttpClient: OkHttpClient, tikXml: TikXml): Retrofit {
    return Retrofit.Builder()
        .client(ojpHttpClient)
        .addConverterFactory(TikXmlConverterFactory.create(tikXml))
        .build()
}

fun provideTikXml(): TikXml {
    return TikXml.Builder()
        .addTypeConverter(String::class.java, HtmlEscapeStringConverter())
        .exceptionOnUnreadXml(false)
        .build()
}
