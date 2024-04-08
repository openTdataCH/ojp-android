package ch.opentransportdata.ojp

import android.app.Application
import timber.log.Timber

/**
 * Created by Michael Ruppen on 08.04.2024
 */
class OjpApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        initializeLogging()
    }

    private fun initializeLogging() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}