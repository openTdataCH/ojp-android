package ch.opentransportdata.ojp.data.local.trip

import ch.opentransportdata.ojp.data.dto.OjpDto
import java.io.InputStream

/**
 * Created by Michael Ruppen on 19.09.2024
 */
internal interface LocalTripDataSource {
    suspend fun requestMockTrips(stream: InputStream): OjpDto
}