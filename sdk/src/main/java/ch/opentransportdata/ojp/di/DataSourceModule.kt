package ch.opentransportdata.ojp.di

import ch.opentransportdata.ojp.data.remote.location.RemoteLocationInformationDataSource
import ch.opentransportdata.ojp.data.remote.location.RemoteLocationInformationDataSourceImpl
import ch.opentransportdata.ojp.data.remote.trip.RemoteTripDataSource
import ch.opentransportdata.ojp.data.remote.trip.RemoteTripDataSourceImpl
import org.koin.dsl.module

/**
 * Created by Michael Ruppen on 08.04.2024
 */
internal val dataSourceModule = module {
    single<RemoteLocationInformationDataSource> { RemoteLocationInformationDataSourceImpl(get(), get()) }
    single<RemoteTripDataSource> { RemoteTripDataSourceImpl(get(), get()) }
}