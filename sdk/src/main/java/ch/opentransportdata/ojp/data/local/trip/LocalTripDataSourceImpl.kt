package ch.opentransportdata.ojp.data.local.trip

import ch.opentransportdata.ojp.data.dto.OjpDto
import com.tickaroo.tikxml.TikXml
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.buffer
import okio.source
import java.io.InputStream

/**
 * Created by Michael Ruppen on 19.09.2024
 */
internal class LocalTripDataSourceImpl(
    private val tikXml: TikXml
) : LocalTripDataSource {

    override suspend fun requestMockTrips(stream: InputStream): OjpDto = withContext(Dispatchers.IO) {
        val bufferedSource = stream.source().buffer()
        val response = tikXml.read<OjpDto>(bufferedSource, OjpDto::class.java)
        return@withContext response
    }

}