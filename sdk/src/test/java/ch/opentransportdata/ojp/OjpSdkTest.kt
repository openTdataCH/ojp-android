package ch.opentransportdata.ojp

import assertk.assertFailure
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import assertk.assertions.isNotEmpty
import ch.opentransportdata.ojp.data.dto.OjpDto
import ch.opentransportdata.ojp.domain.model.PlaceTypeRestriction
import ch.opentransportdata.ojp.domain.model.Result
import ch.opentransportdata.ojp.domain.model.error.OjpError
import com.tickaroo.tikxml.TikXml
import kotlinx.coroutines.test.runTest
import okio.buffer
import okio.source
import org.junit.Test
import java.io.ByteArrayInputStream
import java.io.InputStream


/**
 * Created by Deniz Kalem on 07.05.2024
 */
internal class OjpSdkTest {

    @Test
    fun `Invalid XML data should throw an exception`() {
        // GIVEN
        val invalidXmlFile = """
            <xml>
                <field1>Value1</field1>
                <field2>Value2</field2>
            </xml>
        """.trimIndent()

        val inputStream: InputStream = ByteArrayInputStream(invalidXmlFile.toByteArray())
        val bufferedSource = inputStream.source().buffer()
        val tikXml = TikXml.Builder().exceptionOnUnreadXml(false).build()

        // ASSERTION
        assertFailure { tikXml.read<OjpDto>(bufferedSource, OjpDto::class.java) }
    }

    @Test
    fun `requestLocationsFromSearchTerm with valid data should return a list of places`() {
        runTest {
            // GIVEN
            val ojpSdk = OjpSdk(
                baseUrl = "https://odpch-api.clients.liip.ch/",
                endpoint = "ojp20-beta",
                httpHeaders = hashMapOf("Authorization" to "Bearer eyJvcmciOiI2M2Q4ODhiMDNmZmRmODAwMDEzMDIwODkiLCJpZCI6IjUzYzAyNWI2ZTRhNjQyOTM4NzMxMDRjNTg2ODEzNTYyIiwiaCI6Im11cm11cjEyOCJ9"),
                requesterReference = "OJP_Demo"
            )
            val term = "Bern"

            // ACTION
            val result = ojpSdk.requestLocationsFromSearchTerm(
                term = term, restrictions = listOf(PlaceTypeRestriction.STOP, PlaceTypeRestriction.TOPOGRAPHIC_PLACE)
            )

            // ASSERTION
            assertThat(result).isInstanceOf(Result.Success::class.java)
            assertThat((result as Result.Success).data).isNotEmpty()
        }
    }

    @Test
    fun `requestLocationsFromSearchTerm with empty bearer should return UNEXPECTED_HTTP_STATUS`() {
        runTest {
            // GIVEN
            val ojpSdk = OjpSdk(
                baseUrl = "https://odpch-api.clients.liip.ch/",
                endpoint = "ojp20-beta",
                httpHeaders = hashMapOf("Authorization" to ""),
                requesterReference = "OJP_Demo"
            )
            val term = "Bern"

            // ACTION
            val result = ojpSdk.requestLocationsFromSearchTerm(
                term = term, restrictions = listOf(PlaceTypeRestriction.STOP, PlaceTypeRestriction.TOPOGRAPHIC_PLACE)
            )

            // ASSERTION
            assertThat(result).isInstanceOf(Result.Error::class.java)
            assertThat((result as Result.Error).error).isEqualTo(OjpError.UNEXPECTED_HTTP_STATUS)
        }
    }
}