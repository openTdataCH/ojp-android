package ch.opentransportdata.ojp.di

import ch.opentransportdata.ojp.data.repository.OjpRepositoryImpl
import ch.opentransportdata.ojp.domain.repository.OjpRepository
import org.koin.dsl.module

/**
 * Created by Michael Ruppen on 08.04.2024
 */
internal val repositoryModule = module {
    single<OjpRepository> { OjpRepositoryImpl(get(), get(), get()) }
}