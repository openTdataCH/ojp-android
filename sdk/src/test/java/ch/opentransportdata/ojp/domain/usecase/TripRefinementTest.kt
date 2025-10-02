package ch.opentransportdata.ojp.domain.usecase

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import assertk.assertions.isNotEqualTo
import assertk.assertions.isNotNull
import assertk.fail
import ch.opentransportdata.ojp.BuildConfig
import ch.opentransportdata.ojp.OjpSdk
import ch.opentransportdata.ojp.data.dto.OjpDto
import ch.opentransportdata.ojp.data.dto.converter.FareClassSerializer
import ch.opentransportdata.ojp.data.dto.converter.LocalDateTimeSerializer
import ch.opentransportdata.ojp.data.dto.request.tir.PlaceReferenceDto
import ch.opentransportdata.ojp.data.dto.response.GeoPositionDto
import ch.opentransportdata.ojp.data.dto.response.NameDto
import ch.opentransportdata.ojp.data.dto.response.delivery.TripDeliveryDto
import ch.opentransportdata.ojp.data.dto.response.tir.TripResultDto
import ch.opentransportdata.ojp.data.dto.response.tir.minimalTripResult
import ch.opentransportdata.ojp.domain.model.FareClass
import ch.opentransportdata.ojp.domain.model.LanguageCode
import ch.opentransportdata.ojp.domain.model.RealtimeData
import ch.opentransportdata.ojp.domain.model.Result
import ch.opentransportdata.ojp.domain.model.TripParams
import ch.opentransportdata.ojp.domain.model.TripRefineParam
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.modules.SerializersModule
import nl.adaptivity.xmlutil.ExperimentalXmlUtilApi
import nl.adaptivity.xmlutil.serialization.XML
import nl.adaptivity.xmlutil.serialization.XmlConfig
import org.junit.Before
import org.junit.Test
import java.io.File
import java.time.LocalDateTime
import java.time.ZoneId

/**
 * Created by Nico Brandenberger on 09.04.2025
 */
class TripRefinementXmlUtilTest {

    private val initializer = Initializer()

    @Before
    fun setUp() {
        initializer.defaultTimeZone = ZoneId.of("Europe/Zurich")
    }

    private fun readResource(path: String): String = File("src/test/resources/$path").readText()

    @OptIn(ExperimentalXmlUtilApi::class)
    private fun xml(): XML = XML(
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

    @Test
    fun `Parse trip delivery successful`() {
        // GIVEN
        val xmlText = readResource("trip/trip_delivery.xml")
        val xml = xml()

        // ACTION
        val result = xml.decodeFromString<TripDeliveryDto>(xmlText)

        // ASSERTION
        assertThat(result).isNotNull()
        assertThat(result.tripResults?.size).isEqualTo(10)
    }

    @Test
    fun `Parse long trip result successful`() {
        // GIVEN
        val xmlText = readResource("trr/trip_result_long.xml")
        val xml = xml()

        // ACTION
        val result = xml.decodeFromString<TripResultDto>(xmlText)

        // ASSERTION
        assertThat(result).isNotNull()
    }

    @Test
    fun `Parse short trip result successful`() {
        // GIVEN
        val xmlText = readResource("trr/trip_result_short.xml")
        val xml = xml()

        // ACTION
        val result = xml.decodeFromString<TripResultDto>(xmlText)

        // ASSERTION
        assertThat(result).isNotNull()
        assertThat(result.minimalTripResult).isEqualTo(result)
    }

    @Test
    fun `Request trip refinement from result`() {
        runTest {
            // GIVEN
            val ojpSdk = OjpSdk(
                baseUrl = "https://odpch-api.clients.liip.ch/",
                endpoint = "ojp20-beta",
                httpHeaders = hashMapOf("Authorization" to BuildConfig.API_KEY),
                requesterReference = "OJP_Demo"
            )

            val origin = PlaceReferenceDto(
                ref = "8503000",
                stationName = NameDto(text = "Zürich"),
                position = GeoPositionDto(longitude = 8.54021, latitude = 47.37818)
            )

            val destination = PlaceReferenceDto(
                ref = "8507000",
                stationName = NameDto(text = "Bern"),
                position = GeoPositionDto(longitude = 8.54021, latitude = 47.37818)
            )

            val params = TripParams(
                numberOfResults = 10,
                includeIntermediateStops = true,
                includeAllRestrictedLines = true,
                modeAndModeOfOperationFilter = null,
                useRealtimeData = RealtimeData.FULL
            )

            val tripRequest = ojpSdk.requestTrips(
                languageCode = LanguageCode.DE,
                origin = origin,
                destination = destination,
                time = LocalDateTime.now(),
                params = params
            )

            var tripResult: TripResultDto? = null

            when (tripRequest) {
                is Result.Success -> {
                    if (!tripRequest.data.tripResults.isNullOrEmpty()) {
                        tripResult = tripRequest.data.tripResults!!.first()
                    }
                }

                is Result.Error -> {
                    fail(tripRequest.error.exception.message ?: "Trip Request to OJP failed")
                }
            }

            if (tripResult != null) {
                val result = ojpSdk.requestTripRefinement(
                    languageCode = LanguageCode.DE,
                    tripResult = tripResult,
                    params = TripRefineParam(
                        true,
                        includeLegProjection = true,
                        includeTurnDescription = true,
                        includeIntermediateStops = true,
                        includeAllRestrictedLines = true,
                        useRealtimeData = RealtimeData.EXPLANATORY
                    ),
                )

                // ASSERTION
                assertThat(result).isInstanceOf(Result.Success::class.java)
                assertThat((result as Result.Success).data.tripResults)
                    .isNotEqualTo(emptyList<TripResultDto>())
            }
        }
    }

    @Test
    fun `Serialize and deserialize valid response`() {
        // GIVEN
        val xmlText = readResource("response_valid_2.xml")
        val xml = xml()

        // ACTION: decode -> encode -> decode
        val dto = xml.decodeFromString<OjpDto>(xmlText)
        val xmlRoundTrip = xml.encodeToString(dto)
        val dtoRoundTrip = xml.decodeFromString<OjpDto>(xmlRoundTrip)

        // ASSERTION
        assertThat(dto).isNotNull()
        assertThat(dtoRoundTrip).isEqualTo(dto)
    }
}