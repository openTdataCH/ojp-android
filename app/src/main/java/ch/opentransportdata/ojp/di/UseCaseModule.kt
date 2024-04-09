package ch.opentransportdata.ojp.di

import ch.opentransportdata.ojp.domain.usecase.Initializer
import ch.opentransportdata.ojp.domain.usecase.RequestLocationByCoordinates
import ch.opentransportdata.ojp.domain.usecase.RequestLocationBySearchTerm
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

/**
 * Created by Michael Ruppen on 08.04.2024
 */
internal val useCaseModule = module {
    singleOf(::Initializer)
    factoryOf(::RequestLocationBySearchTerm)
    factoryOf(::RequestLocationByCoordinates)
}