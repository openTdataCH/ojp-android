package ch.opentransportdata.ojp

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import assertk.assertions.isNotEmpty
import assertk.assertions.isNotNull
import ch.opentransportdata.ojp.data.dto.OjpDto
import ch.opentransportdata.ojp.data.dto.converter.PtModeTypeConverter
import ch.opentransportdata.ojp.domain.model.PlaceTypeRestriction
import ch.opentransportdata.ojp.domain.model.PtMode
import ch.opentransportdata.ojp.domain.model.Result
import ch.opentransportdata.ojp.domain.model.error.OjpError
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.TypeConverterNotFoundException
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertThrows
import org.junit.Test


/**
 * Created by Deniz Kalem on 07.05.2024
 */
internal class OjpSdkTest {

    @Test
    fun `XML data that contains the custom data type PtMode which is not registered should throw a TypeConverterNotFoundException`() {
        // GIVEN
        val xmlFile = "src/test/resources/response_custom_data_type_ptmode.xml"
        val bufferedSource = TestUtils().readXmlFile(xmlFile)
        val tikXml = TikXml.Builder().build()

        // ACTION
        val parsingAction: () -> Unit = { tikXml.read<OjpDto>(bufferedSource, OjpDto::class.java) }

        // ASSERTION
        assertThrows(TypeConverterNotFoundException::class.java, parsingAction)
    }

    @Test
    fun `XML data that contains the custom data type PtMode which is registered should allow successful parsing to an OjpDto`() {
        // GIVEN
        val xmlFile = "src/test/resources/response_custom_data_type_ptmode.xml"
        val bufferedSource = TestUtils().readXmlFile(xmlFile)
        val tikXml = TikXml.Builder().addTypeConverter(PtMode::class.java, PtModeTypeConverter()).build()

        // ACTION
        val result = tikXml.read<OjpDto>(bufferedSource, OjpDto::class.java)

        // ASSERTION
        assertThat(result).isNotNull()
    }

    @Test
    fun `Missing element in XML data should throw a NullPointerException`() {
        // GIVEN
        val xmlFile = "src/test/resources/response_missing_element_requestorref.xml"
        val bufferedSource = TestUtils().readXmlFile(xmlFile)
        val tikXml = TikXml.Builder().build()

        // ACTION
        val parsingAction: () -> Unit = { tikXml.read<OjpDto>(bufferedSource, OjpDto::class.java) }

        // ASSERTION
        assertThrows(NullPointerException::class.java, parsingAction)
    }

    @Test
    fun `Valid XML data should allow successful parsing to an OjpDto`() {
        // GIVEN
        val xmlFile = "src/test/resources/response_valid.xml"
        val bufferedSource = TestUtils().readXmlFile(xmlFile)
        val tikXml = TikXml.Builder().build()

        // ACTION
        val result = tikXml.read<OjpDto>(bufferedSource, OjpDto::class.java)

        // ASSERTION
        assertThat(result).isNotNull()
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