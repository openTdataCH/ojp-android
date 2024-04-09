package ch.opentransportdata.ojp.di

import ch.opentransportdata.ojp.data.repository.OjpRepositoryImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

/**
 * Created by Michael Ruppen on 08.04.2024
 */
internal val repositoryModule = module {
    singleOf(::OjpRepositoryImpl)
}