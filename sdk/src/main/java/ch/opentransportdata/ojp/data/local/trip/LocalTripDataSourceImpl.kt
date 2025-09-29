package ch.opentransportdata.ojp.data.local.trip

import ch.opentransportdata.ojp.data.dto.OjpDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import nl.adaptivity.xmlutil.serialization.XML
import okio.buffer
import okio.source
import java.io.InputStream

/**
 * Created by Michael Ruppen on 19.09.2024
 */
internal class LocalTripDataSourceImpl(
    private val xml: XML
) : LocalTripDataSource {

    override suspend fun requestMockTrips(stream: InputStream): OjpDto = withContext(Dispatchers.IO) {
        val xmlString = stream.source().buffer().readUtf8()
        xml.decodeFromString<OjpDto>(xmlString)
    }
}