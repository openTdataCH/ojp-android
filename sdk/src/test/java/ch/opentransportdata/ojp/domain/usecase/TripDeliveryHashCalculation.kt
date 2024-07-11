package ch.opentransportdata.ojp.domain.usecase

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import ch.opentransportdata.ojp.TestUtils
import ch.opentransportdata.ojp.data.dto.adapter.PlaceAdapter
import ch.opentransportdata.ojp.data.dto.adapter.ServiceDeliveryAdapter
import ch.opentransportdata.ojp.data.dto.adapter.TripResultAdapter
import ch.opentransportdata.ojp.data.dto.converter.*
import ch.opentransportdata.ojp.data.dto.response.PlaceDto
import ch.opentransportdata.ojp.data.dto.response.ServiceDeliveryDto
import ch.opentransportdata.ojp.data.dto.response.delivery.TripDeliveryDto
import ch.opentransportdata.ojp.data.dto.response.tir.TripResultDto
import ch.opentransportdata.ojp.data.dto.response.tir.trips.TripDto
import ch.opentransportdata.ojp.domain.model.ConventionalModesOfOperation
import ch.opentransportdata.ojp.domain.model.PlaceTypeRestriction
import ch.opentransportdata.ojp.domain.model.PtMode
import ch.opentransportdata.ojp.domain.model.TransferType
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.converter.htmlescape.HtmlEscapeStringConverter
import org.junit.Before
import org.junit.Test

/**
 * Created by Michael Ruppen on 10.07.2024
 */
internal class TripDeliveryHashCalculation {

    private lateinit var tikXml: TikXml

    @Before
    fun setUp() {
        tikXml = TikXml.Builder()
            .addTypeAdapter(PlaceDto::class.java, PlaceAdapter())
            .addTypeAdapter(ServiceDeliveryDto::class.java, ServiceDeliveryAdapter())
            .addTypeAdapter(TripResultDto::class.java, TripResultAdapter())
            .addTypeConverter(java.time.LocalDateTime::class.java, LocalDateTimeTypeConverter())
            .addTypeConverter(String::class.java, HtmlEscapeStringConverter())
            .addTypeConverter(PtMode::class.java, PtModeTypeConverter())
            .addTypeConverter(PlaceTypeRestriction::class.java, PlaceTypeRestrictionConverter())
            .addTypeConverter(TransferType::class.java, TransferTypeConverter())
            .addTypeConverter(ConventionalModesOfOperation::class.java, ConventionalModesOfOperationConverter())
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
        assertThat(result.tripResults.size).isEqualTo(10)
    }

    @Test
    fun `Filter duplicate trips, only 10 should be returned (even though 13 are parsed)`() {
        // GIVEN
        val xmlFile = "src/test/resources/trip/trip_delivery_same_trips.xml"
        val bufferedSource = TestUtils().readXmlFile(xmlFile)

        // ACTION
        val result = tikXml.read<TripDeliveryDto>(bufferedSource, TripDeliveryDto::class.java)
        val filteredList = filterDuplicatedTrips(result.tripResults, mutableListOf())

        // ASSERTION
        assertThat(result).isNotNull()
        assertThat(result.tripResults.size).isEqualTo(13)
        assertThat(filteredList.size).isEqualTo(10)

    }

    @Test
    fun `Filter duplicate trips with hashList contains one element already, only 9 should be returned (even though 13 are parsed)`() {
        // GIVEN
        val xmlFile = "src/test/resources/trip/trip_delivery_same_trips.xml"
        val bufferedSource = TestUtils().readXmlFile(xmlFile)

        // ACTION
        val result = tikXml.read<TripDeliveryDto>(bufferedSource, TripDeliveryDto::class.java)
        val filteredList = filterDuplicatedTrips(result.tripResults, mutableListOf(941504869))

        // ASSERTION
        assertThat(result).isNotNull()
        assertThat(result.tripResults.size).isEqualTo(13)
        assertThat(filteredList.size).isEqualTo(9)

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