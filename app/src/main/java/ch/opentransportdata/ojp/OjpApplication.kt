package ch.opentransportdata.ojp

import android.app.Application
import ch.opentransportdata.ojp.di.dataSourceModule
import ch.opentransportdata.ojp.di.networkModule
import ch.opentransportdata.ojp.di.repositoryModule
import ch.opentransportdata.ojp.di.useCaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import timber.log.Timber

/**
 * Created by Michael Ruppen on 08.04.2024
 */
internal class OjpApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initializeLogging()
        startKoin {
            androidContext(this@OjpApplication)
            modules(listOf(networkModule, repositoryModule, dataSourceModule, useCaseModule))
        }
    }

    private fun initializeLogging() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}