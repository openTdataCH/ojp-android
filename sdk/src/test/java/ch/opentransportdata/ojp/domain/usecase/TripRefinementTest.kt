package ch.opentransportdata.ojp.domain.usecase

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import assertk.assertions.isNotEqualTo
import assertk.assertions.isNotNull
import assertk.fail
import ch.opentransportdata.ojp.BuildConfig
import ch.opentransportdata.ojp.OjpSdk
import ch.opentransportdata.ojp.TestUtils
import ch.opentransportdata.ojp.data.dto.OjpDto
import ch.opentransportdata.ojp.data.dto.converter.ConventionalModesOfOperationConverter
import ch.opentransportdata.ojp.data.dto.converter.DurationTypeConverter
import ch.opentransportdata.ojp.data.dto.converter.LocalDateTimeTypeConverter
import ch.opentransportdata.ojp.data.dto.converter.PlaceTypeRestrictionConverter
import ch.opentransportdata.ojp.data.dto.converter.PtModeTypeConverter
import ch.opentransportdata.ojp.data.dto.converter.RealTimeDataConverter
import ch.opentransportdata.ojp.data.dto.converter.ScopeTypeConverter
import ch.opentransportdata.ojp.data.dto.converter.TransferTypeConverter
import ch.opentransportdata.ojp.data.dto.request.tir.PlaceReferenceDto
import ch.opentransportdata.ojp.data.dto.response.GeoPositionDto
import ch.opentransportdata.ojp.data.dto.response.NameDto
import ch.opentransportdata.ojp.data.dto.response.delivery.TripDeliveryDto
import ch.opentransportdata.ojp.data.dto.response.tir.TripResultDto
import ch.opentransportdata.ojp.data.dto.response.tir.minimalTripResult
import ch.opentransportdata.ojp.data.dto.response.tir.trips.TripDto
import ch.opentransportdata.ojp.domain.model.ConventionalModesOfOperation
import ch.opentransportdata.ojp.domain.model.LanguageCode
import ch.opentransportdata.ojp.domain.model.PlaceTypeRestriction
import ch.opentransportdata.ojp.domain.model.PtMode
import ch.opentransportdata.ojp.domain.model.RealtimeData
import ch.opentransportdata.ojp.domain.model.Result
import ch.opentransportdata.ojp.domain.model.ScopeType
import ch.opentransportdata.ojp.domain.model.TransferType
import ch.opentransportdata.ojp.domain.model.TripParams
import ch.opentransportdata.ojp.domain.model.TripRefineParam
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.converter.htmlescape.HtmlEscapeStringConverter
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.test.runTest
import okio.buffer
import okio.sink
import org.junit.Before
import org.junit.Test
import java.io.ByteArrayOutputStream
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneId

/**
 * Created by Nico Brandenberger on 09.04.2025
 */

class TripRefinementTest {
    private lateinit var tikXml: TikXml
    private val initializer = Initializer()

    @Before
    fun setUp() {
        initializer.defaultTimeZone = ZoneId.of("Europe/Zurich")
        tikXml = TikXml.Builder()
            .addTypeConverter(java.time.LocalDateTime::class.java, LocalDateTimeTypeConverter(initializer))
            .addTypeConverter(Duration::class.java, DurationTypeConverter())
            .addTypeConverter(String::class.java, HtmlEscapeStringConverter())
            .addTypeConverter(PtMode::class.java, PtModeTypeConverter())
            .addTypeConverter(PlaceTypeRestriction::class.java, PlaceTypeRestrictionConverter())
            .addTypeConverter(TransferType::class.java, TransferTypeConverter())
            .addTypeConverter(ConventionalModesOfOperation::class.java, ConventionalModesOfOperationConverter())
            .addTypeConverter(RealtimeData::class.java, RealTimeDataConverter())
            .addTypeConverter(ScopeType::class.java, ScopeTypeConverter())
            .exceptionOnUnreadXml(false)
            .build()
    }

    @Test
    fun `Parse trip delivery successful`() {
        // GIVEN
        val xmlFile = "src/test/resources/trip/trip_delivery.xml"
        val bufferedSource = TestUtils().readXmlFile(xmlFile)

        // ACTION
        val result = tikXml.read<TripDeliveryDto>(bufferedSource, TripDeliveryDto::class.java)

        // ASSERTION
        assertThat(result).isNotNull()
        assertThat(result.tripResults?.size).isEqualTo(10)
    }


    @Test
    fun `Parse long trip result successful`() {
        // GIVEN
        val xmlFile = "src/test/resources/trr/trip_result_long.xml"
        val bufferedSource = TestUtils().readXmlFile(xmlFile)

        // ACTION
        val result = tikXml.read<TripResultDto>(bufferedSource, TripResultDto::class.java)

        // ASSERTION
        assertThat(result).isNotNull()
    }

    @Test
    fun `Parse short trip result successful`() {
        // GIVEN
        val xmlFile = "src/test/resources/trr/trip_result_short.xml"
        val bufferedSource = TestUtils().readXmlFile(xmlFile)

        // ACTION
        val result = tikXml.read<TripResultDto>(bufferedSource, TripResultDto::class.java)

        // ASSERTION
        assertThat(result).isNotNull()
        assertThat(result.minimalTripResult).isEqualTo(result)
    }

    // @Test
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
                GeoPositionDto(longitude = 8.54021, latitude = 47.37818)
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

            //ACTION
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
                        true,
                        true,
                        true,
                        true,
                        RealtimeData.EXPLANATORY
                    ),
                )

                // ASSERTION
                assertThat(result).isInstanceOf(Result.Success::class.java)
                assertThat((result as Result.Success).data.tripResults).isNotEqualTo(emptyList<TripResultDto>())
            }
        }
    }

    @Test
    fun `Serialize and deserialize valid response`() {
        //GIVEN
        val xmlFile = "src/test/resources/response_valid.xml"
        val bufferedSource = TestUtils().readXmlFile(xmlFile)
        val byteArrayOutputStream = ByteArrayOutputStream()
        val bufferedSink = byteArrayOutputStream.sink().buffer()

        //ACTION
        val request = tikXml.read<OjpDto>(bufferedSource, OjpDto::class.java)

        tikXml.write(bufferedSink, request)
        bufferedSink.flush()
        val xmlString = byteArrayOutputStream.toString("UTF-8")
        println("Re-serialized OjpRequest XML:\n$xmlString")

        //ASSERTION
        assertNotNull(request)
        assertThat(xmlString.contains("<RequestorRef>OJP_Demo_ANDROID_SDK_1.0.14</RequestorRef>"))
    }
}