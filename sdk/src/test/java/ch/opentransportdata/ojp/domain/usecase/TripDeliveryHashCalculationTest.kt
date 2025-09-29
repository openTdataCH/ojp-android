package ch.opentransportdata.ojp.domain.usecase

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import ch.opentransportdata.ojp.data.dto.converter.FareClassSerializer
import ch.opentransportdata.ojp.data.dto.response.delivery.TripDeliveryDto
import ch.opentransportdata.ojp.data.dto.response.tir.TripResultDto
import ch.opentransportdata.ojp.data.dto.response.tir.trips.TripDto
import ch.opentransportdata.ojp.domain.model.FareClass
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.modules.SerializersModule
import nl.adaptivity.xmlutil.ExperimentalXmlUtilApi
import nl.adaptivity.xmlutil.serialization.XML
import nl.adaptivity.xmlutil.serialization.XmlConfig
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.File
import java.time.ZoneId

/**
 * Created by Michael Ruppen on 10.07.2024
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [35])
internal class TripDeliveryHashCalculationTest {
    private val initializer = Initializer()

    @Before
    fun setUp() {
        initializer.defaultTimeZone = ZoneId.of("Europe/Zurich")
    }

    private fun readResource(path: String): String = File("src/test/resources/$path").readText()

    @OptIn(ExperimentalXmlUtilApi::class)
    private fun xml(): XML = XML(
        serializersModule = SerializersModule {
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
        val xmlFile = readResource("trip/trip_delivery.xml")
        val xml = xml()

        // ACTION
        val result = xml.decodeFromString<TripDeliveryDto>(xmlFile)

        // ASSERTION
        assertThat(result).isNotNull()
        assertThat(result.tripResults?.size).isEqualTo(10)
    }

    @Test
    fun `Filter duplicate trips, only 10 should be returned (even though 13 are parsed)`() {
        // GIVEN
        val xmlFile = readResource("trip/trip_delivery_same_trips.xml")
        val xml = xml()

        // ACTION
        val result = xml.decodeFromString<TripDeliveryDto>(xmlFile)
        val filteredList = result.tripResults?.let { filterDuplicatedTrips(it, mutableListOf()) }

        // ASSERTION
        assertThat(result).isNotNull()
        assertThat(result.tripResults?.size).isEqualTo(13)
        assertThat(filteredList?.size).isEqualTo(10)

    }

    @Test
    fun `Filter duplicate trips with hashList contains one element already, only 9 should be returned (even though 13 are parsed)`() {
        // GIVEN
        val xmlFile = readResource("trip/trip_delivery_same_trips.xml")
        val xml = xml()

        // ACTION
        val result = xml.decodeFromString<TripDeliveryDto>(xmlFile)
        val filteredList = result.tripResults?.let { filterDuplicatedTrips(it, mutableListOf(2099078496)) }

        // ASSERTION
        assertThat(result).isNotNull()
        assertThat(result.tripResults?.size).isEqualTo(13)
        assertThat(filteredList?.size).isEqualTo(10)

    }

    private fun filterDuplicatedTrips(tripResults: List<TripResultDto>, existingHashes: MutableList<Int>): List<TripResultDto> {
        return tripResults
            .filter { it.trip is TripDto }
            .mapNotNull { tripResult ->
                val hash = (tripResult.trip as TripDto).hashCode()
                when (existingHashes.contains(hash)) {
                    false -> {
                        existingHashes.add(hash)
                        tripResult
                    }

                    true -> null
                }
            }
    }
}