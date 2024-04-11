package ch.opentransportdata.ojp.di

import ch.opentransportdata.ojp.domain.usecase.Initializer
import ch.opentransportdata.ojp.domain.usecase.RequestLocationsFromCoordinates
import ch.opentransportdata.ojp.domain.usecase.RequestLocationsFromSearchTerm
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

/**
 * Created by Michael Ruppen on 08.04.2024
 */
val useCaseModule = module {
    singleOf(::Initializer)
    factoryOf(::RequestLocationsFromSearchTerm)
    factoryOf(::RequestLocationsFromCoordinates)
}