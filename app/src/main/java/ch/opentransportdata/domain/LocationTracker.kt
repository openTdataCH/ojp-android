package ch.opentransportdata.domain

import android.location.Location

/**
 * Created by Michael Ruppen on 25.04.2024
 */
interface LocationTracker {
    suspend fun getCurrentLocation(): Location?
}