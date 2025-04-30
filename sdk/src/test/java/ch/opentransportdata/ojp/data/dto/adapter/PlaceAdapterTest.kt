package ch.opentransportdata.ojp.data.dto.adapter

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import assertk.assertions.isNull
import ch.opentransportdata.ojp.TestUtils
import ch.opentransportdata.ojp.data.dto.converter.PtModeTypeConverter
import ch.opentransportdata.ojp.data.dto.response.PlaceDto
import ch.opentransportdata.ojp.data.dto.response.place.AddressDto
import ch.opentransportdata.ojp.data.dto.response.place.StopPlaceDto
import ch.opentransportdata.ojp.data.dto.response.place.TopographicPlaceDto
import ch.opentransportdata.ojp.domain.model.PtMode
import com.tickaroo.tikxml.TikXml
import org.junit.Assert.assertThrows
import org.junit.Test

/**
 * Created by Michael Ruppen on 17.05.2024
 */
internal class PlaceAdapterTest {

    private val tikXml = TikXml.Builder()
        .addTypeConverter(PtMode::class.java, PtModeTypeConverter())
        .build()

    @Test
    fun `Place type StopPlace, parsing successful`() {
        // GIVEN
        val xmlFile = "src/test/resources/adapter/place/stop_place.xml"
        val bufferedSource = TestUtils().readXmlFile(xmlFile)

        // ACTION
        val result = tikXml.read<PlaceDto>(bufferedSource, PlaceDto::class.java)

        // ASSERTION
        assertThat(result).isNotNull()
        assertThat(result.placeType?.javaClass).isEqualTo(StopPlaceDto::class.java)

        val stopPlace = result.placeType as StopPlaceDto
        assertThat(stopPlace.stopPlaceRef).isEqualTo("8501120")
        assertThat(stopPlace.name?.text).isEqualTo("Lausanne")
        assertThat(stopPlace.topographicPlaceRef).isEqualTo("23023586:3")
        assertThat(result.name?.text).isEqualTo("Lausanne (Lausanne)")
        assertThat(result.position?.latitude).isEqualTo(46.51679)
        assertThat(result.position?.longitude).isEqualTo(6.62909)
        assertThat(result.mode?.first()?.ptMode).isEqualTo(PtMode.RAIL)
    }

    @Test
    fun `Place type Address, parsing successful`() {
        // GIVEN
        val xmlFile = "src/test/resources/adapter/place/address.xml"
        val bufferedSource = TestUtils().readXmlFile(xmlFile)

        // ACTION
        val result = tikXml.read<PlaceDto>(bufferedSource, PlaceDto::class.java)

        // ASSERTION
        assertThat(result).isNotNull()
        assertThat(result.placeType?.javaClass).isEqualTo(AddressDto::class.java)

        val stopPlace = result.placeType as AddressDto
        assertThat(stopPlace.name.text).isEqualTo("Bern, Eichholzstrasse")
        assertThat(stopPlace.postCode).isEqualTo("3032 3027")
        assertThat(stopPlace.topographicPlaceName?.text).isEqualTo("Bern")
        assertThat(stopPlace.topographicPlaceRef).isEqualTo("23006351:-1")
        assertThat(stopPlace.street).isEqualTo("Eichholzstrasse")
        assertThat(stopPlace.countryName).isNull()
        assertThat(stopPlace.crossRoad).isNull()
        assertThat(stopPlace.houseNumber).isNull()
        assertThat(result.name?.text).isEqualTo("Eichholzstrasse (Bern)")
        assertThat(result.position?.latitude).isEqualTo(46.95054)
        assertThat(result.position?.longitude).isEqualTo(7.3861)
        assertThat(result.mode).isNull()
    }

    @Test
    fun `No placeType available, parsing successful`() {
        // GIVEN
        val xmlFile = "src/test/resources/adapter/place/no_place_type_available.xml"
        val bufferedSource = TestUtils().readXmlFile(xmlFile)

        // ACTION
        val result = tikXml.read<PlaceDto>(bufferedSource, PlaceDto::class.java)

        // ASSERTION
        assertThat(result).isNotNull()
        assertThat(result.placeType).isNull()

        assertThat(result.name?.text).isEqualTo("Lausanne (Lausanne)")
        assertThat(result.position?.latitude).isEqualTo(46.51679)
        assertThat(result.position?.longitude).isEqualTo(6.62909)
        assertThat(result.mode?.first()?.ptMode).isEqualTo(PtMode.RAIL)
    }

    @Test
    fun `Missing mandatory field position, parsing failed`() {
        // GIVEN
        val xmlFile = "src/test/resources/adapter/place/no_position.xml"
        val bufferedSource = TestUtils().readXmlFile(xmlFile)

        // ACTION
        val parsingAction: () -> Unit = { tikXml.read<PlaceDto>(bufferedSource, PlaceDto::class.java) }

        // ASSERTION
        assertThrows(NullPointerException::class.java, parsingAction)
    }

    @Test
    fun `TopographicPlace type, parsing successful`() {
        // GIVEN
        val xmlFile = "src/test/resources/adapter/place/topographic_place.xml"
        val bufferedSource = TestUtils().readXmlFile(xmlFile)

        // ACTION
        val result = tikXml.read<PlaceDto>(bufferedSource, PlaceDto::class.java)

        // ASSERTION
        assertThat(result).isNotNull()
        assertThat(result.placeType?.javaClass).isEqualTo(TopographicPlaceDto::class.java)

        val topographicPlace = result.placeType as TopographicPlaceDto
        assertThat(topographicPlace.name.text).isEqualTo("Bernlohe (Aalen)")
        assertThat(topographicPlace.privateCodes).isNull()
        assertThat(topographicPlace.ref).isNull()

        assertThat(result.name?.text).isEqualTo("Bernlohe (Aalen)")
        assertThat(result.position?.latitude).isEqualTo(10.16666)
        assertThat(result.position?.longitude).isEqualTo(48.85)
        assertThat(result.mode).isNull()
    }
}