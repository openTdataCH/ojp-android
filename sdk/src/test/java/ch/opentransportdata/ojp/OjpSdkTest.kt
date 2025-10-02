package ch.opentransportdata.ojp

import assertk.assertThat
import assertk.assertions.isInstanceOf
import assertk.assertions.isNotEmpty
import assertk.assertions.isNotNull
import ch.opentransportdata.ojp.data.dto.OjpDto
import ch.opentransportdata.ojp.data.dto.converter.FareClassSerializer
import ch.opentransportdata.ojp.data.dto.converter.LocalDateTimeSerializer
import ch.opentransportdata.ojp.domain.model.FareClass
import ch.opentransportdata.ojp.domain.model.LanguageCode
import ch.opentransportdata.ojp.domain.model.LocationInformationParams
import ch.opentransportdata.ojp.domain.model.PlaceTypeRestriction
import ch.opentransportdata.ojp.domain.model.Result
import ch.opentransportdata.ojp.domain.model.error.OjpError
import ch.opentransportdata.ojp.domain.usecase.Initializer
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.modules.SerializersModule
import nl.adaptivity.xmlutil.ExperimentalXmlUtilApi
import nl.adaptivity.xmlutil.serialization.XML
import nl.adaptivity.xmlutil.serialization.XmlConfig
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import java.io.File
import java.time.LocalDateTime
import java.time.ZoneId

class OjpSdkXmlUtilTest {

    private val initializer = Initializer()

    @Before
    fun setUp() {
        initializer.defaultTimeZone = ZoneId.of("Europe/Zurich")
    }

    @OptIn(ExperimentalXmlUtilApi::class)
    private fun xmlWithPtMode(): XML = XML(
        serializersModule = SerializersModule {
            contextual(LocalDateTime::class, LocalDateTimeSerializer(initializer.defaultTimeZone))
            contextual(FareClass::class, FareClassSerializer)
        }
    ) {
        repairNamespaces = true
        defaultPolicy {
            unknownChildHandler = XmlConfig.IGNORING_UNKNOWN_CHILD_HANDLER
            verifyElementOrder = false
            throwOnRepeatedElement = false
            isStrictBoolean = true
            isStrictAttributeNames = true
        }
    }

    private fun readText(path: String) = File(path).readText()

    @Test
    fun `XML with PtMode registered should parse to OjpDto`() {
        // GIVEN
        val xmlText = readText("src/test/resources/response_custom_data_type_ptmode.xml")
        val xml = xmlWithPtMode()

        // ACTION
        val result = xml.decodeFromString<OjpDto>(xmlText)

        // ASSERTION
        assertThat(result).isNotNull()
    }

    @Test
    fun `Missing element in XML should throw SerializationException`() {
        // GIVEN
        val xmlText = readText("src/test/resources/response_missing_element_requestorref.xml")
        val xml = xmlWithPtMode()

        // ACTION + ASSERTION
        assertThrows(SerializationException::class.java) {
            xml.decodeFromString<OjpDto>(xmlText)
        }
    }

    @Test
    fun `Valid XML should parse to OjpDto`() {
        // GIVEN
        val xmlText = readText("src/test/resources/response_valid_2.xml")
        val xml = xmlWithPtMode()

        // ACTION
        val result = xml.decodeFromString<OjpDto>(xmlText)

        // ASSERTION
        assertThat(result).isNotNull()
    }

    @Test
    fun `Another valid XML should parse to OjpDto`() {
        val xmlText = File("src/test/resources/response_valid_2.xml").readText()
        val xml = xmlWithPtMode()
        val result = xml.decodeFromString<OjpDto>(xmlText)
        assertThat(result).isNotNull()
    }

    @Test
    fun `requestLocationsFromSearchTerm with valid data should return a list of places`() = runTest {
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
                ptModeIncluded = false
            )
        )

        // ASSERTION
        assertThat(result).isInstanceOf(Result.Success::class.java)
        assertThat((result as Result.Success).data).isNotEmpty()
    }

    @Test
    fun `requestLocationsFromSearchTerm with empty bearer should return UNEXPECTED_HTTP_STATUS`() = runTest {
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
        assertThat((result as Result.Error).error)
            .isInstanceOf(OjpError.UnexpectedHttpStatus::class)
    }
}