package ch.opentransportdata.ojp

import assertk.assertThat
import assertk.assertions.isInstanceOf
import assertk.assertions.isNotEmpty
import assertk.assertions.isNotNull
import ch.opentransportdata.ojp.data.dto.OjpDto
import ch.opentransportdata.ojp.data.dto.converter.DurationTypeConverter
import ch.opentransportdata.ojp.data.dto.converter.LocalDateTimeTypeConverter
import ch.opentransportdata.ojp.data.dto.converter.PtModeTypeConverter
import ch.opentransportdata.ojp.data.dto.converter.TransferTypeConverter
import ch.opentransportdata.ojp.domain.model.*
import ch.opentransportdata.ojp.domain.model.error.OjpError
import ch.opentransportdata.ojp.domain.usecase.Initializer
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.TypeConverterNotFoundException
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import java.time.Duration
import java.time.ZoneId


/**
 * Created by Deniz Kalem on 07.05.2024
 */
internal class OjpSdkTest {

    private val initializer = Initializer()

    @Before
    fun setUp(){
        initializer.defaultTimeZone = ZoneId.of("Europe/Zurich")
    }

    @Test
    fun `XML data that contains the custom data type PtMode which is not registered should throw a TypeConverterNotFoundException`() {
        // GIVEN
        val xmlFile = "src/test/resources/response_custom_data_type_ptmode.xml"
        val bufferedSource = TestUtils().readXmlFile(xmlFile)
        val tikXml = TikXml.Builder()
            .addTypeConverter(java.time.LocalDateTime::class.java, LocalDateTimeTypeConverter(initializer))
            .build()

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
        val tikXml = TikXml.Builder()
            .addTypeConverter(PtMode::class.java, PtModeTypeConverter())
            .addTypeConverter(java.time.LocalDateTime::class.java, LocalDateTimeTypeConverter(initializer))
            .build()

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
        val tikXml = TikXml.Builder()
            .addTypeConverter(java.time.LocalDateTime::class.java, LocalDateTimeTypeConverter(initializer))
            .build()

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
        val tikXml = TikXml.Builder()
            .addTypeConverter(java.time.LocalDateTime::class.java, LocalDateTimeTypeConverter(initializer))
            .build()

        // ACTION
        val result = tikXml.read<OjpDto>(bufferedSource, OjpDto::class.java)

        // ASSERTION
        assertThat(result).isNotNull()
    }

    @Test
    fun `Another valid XML data should allow successful parsing to an OjpDto`() {
        // GIVEN
        val xmlFile = "src/test/resources/response_valid_2.xml"
        val bufferedSource = TestUtils().readXmlFile(xmlFile)
        val tikXml = TikXml.Builder()
            .addTypeConverter(java.time.LocalDateTime::class.java, LocalDateTimeTypeConverter(initializer))
            .addTypeConverter(Duration::class.java, DurationTypeConverter())
            .addTypeConverter(PtMode::class.java, PtModeTypeConverter())
            .addTypeConverter(TransferType::class.java, TransferTypeConverter())
            .build()

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
                httpHeaders = hashMapOf("Authorization" to BuildConfig.API_KEY),
                requesterReference = "OJP_Demo"
            )
            val term = "Bern"

            // ACTION
            val result = ojpSdk.requestLocationsFromSearchTerm(
                languageCode = LanguageCode.DE,
                term = term,
                restrictions = LocationInformationParams(
                    types = listOf(PlaceTypeRestriction.STOP, PlaceTypeRestriction.ADDRESS),
                    numberOfResults = 10,
                    ptModeIncluded = true
                )
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
                languageCode = LanguageCode.DE,
                term = term,
                restrictions = LocationInformationParams(
                    types = listOf(PlaceTypeRestriction.STOP, PlaceTypeRestriction.TOPOGRAPHIC_PLACE),
                    numberOfResults = 10,
                    ptModeIncluded = true
                )
            )

            // ASSERTION
            assertThat(result).isInstanceOf(Result.Error::class.java)
            assertThat((result as Result.Error).error).isInstanceOf(OjpError.UnexpectedHttpStatus::class)
        }
    }

}