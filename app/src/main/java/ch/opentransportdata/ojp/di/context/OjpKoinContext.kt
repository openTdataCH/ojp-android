package ch.opentransportdata.ojp.di.context

import ch.opentransportdata.ojp.di.dataSourceModule
import ch.opentransportdata.ojp.di.networkModule
import ch.opentransportdata.ojp.di.repositoryModule
import ch.opentransportdata.ojp.di.useCaseModule
import org.koin.dsl.koinApplication

/**
 * Created by Michael Ruppen on 08.04.2024
 */
object OjpKoinContext {
    val koinApp = koinApplication {
        modules(listOf(dataSourceModule, networkModule, repositoryModule, useCaseModule))
    }
}
