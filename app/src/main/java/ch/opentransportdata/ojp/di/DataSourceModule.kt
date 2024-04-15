package ch.opentransportdata.ojp.di

import ch.opentransportdata.ojp.data.remote.RemoteOjpDataSource
import ch.opentransportdata.ojp.data.remote.RemoteOjpDataSourceImpl
import org.koin.dsl.module

/**
 * Created by Michael Ruppen on 08.04.2024
 */
internal val dataSourceModule = module {
    single<RemoteOjpDataSource> { RemoteOjpDataSourceImpl(get(), get()) }
}