package ch.opentransportdata.ojp.di

import ch.opentransportdata.ojp.domain.usecase.*
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

/**
 * Created by Michael Ruppen on 08.04.2024
 */
internal val useCaseModule = module {
    singleOf(::Initializer)
    factoryOf(::RequestLocationsFromSearchTerm)
    factoryOf(::RequestLocationsFromCoordinates)
    singleOf(::RequestTrips)
    factoryOf(::RequestMockTrips)
}