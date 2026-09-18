package ch.opentransportdata.ojp.data.dto.response.tr.situations

import assertk.assertThat
import assertk.assertions.isEqualTo
import ch.opentransportdata.ojp.data.dto.OjpDto
import ch.opentransportdata.ojp.data.dto.converter.FareClassSerializer
import ch.opentransportdata.ojp.data.dto.converter.LocalDateTimeSerializer
import ch.opentransportdata.ojp.domain.model.FareClass
import kotlinx.serialization.modules.SerializersModule
import nl.adaptivity.xmlutil.ExperimentalXmlUtilApi
import nl.adaptivity.xmlutil.serialization.XML
import nl.adaptivity.xmlutil.serialization.XmlConfig
import org.junit.Test
import java.io.File
import java.time.LocalDateTime
import java.time.ZoneId

/**
 * Created by Nico Brandenberger on 18.09.2026
 */
class AffectedStopPlaceDtoTest {

    @OptIn(ExperimentalXmlUtilApi::class)
    private val xml = XML(
        serializersModule = SerializersModule {
            contextual(LocalDateTime::class, LocalDateTimeSerializer(ZoneId.of("Europe/Zurich")))
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

    private fun situations(): List<PtSituationDto> {
        val raw = File("src/test/resources/tir/trip_info_affected_stop_places.xml").readText()
        return xml.decodeFromString(OjpDto.serializer(), raw)
            .ojpResponse?.serviceDelivery?.ojpTripInfoDelivery
            ?.responseContext?.situation?.ptSituation.orEmpty()
    }

    private fun PtSituationDto.affectedStopPlaceRefs(): List<String> =
        publishingActions?.publishingActions
            ?.mapNotNull { it.publishAtScope?.affects?.stopPlaces?.affectedStopPlaces }
            ?.flatten()
            ?.mapNotNull { it.stopPlaceRef }
            .orEmpty()

    @Test
    fun `stopPlace scoped situation exposes its affected stop place ref`() {
        // GIVEN
        val situations = situations()

        // ASSERTION
        assertThat(situations.size).isEqualTo(2)
        assertThat(situations.first().situationNumber).isEqualTo("ch:1:sstid:1:MENTZ_SP-2b_2026-09-18-95")
        assertThat(situations.first().affectedStopPlaceRefs()).isEqualTo(listOf("ch:1:sloid:3000"))
        assertThat(situations.last().affectedStopPlaceRefs()).isEqualTo(listOf("ch:1:sloid:3020"))
    }

    @Test
    fun `affected stop place exposes its place name`() {
        // GIVEN
        val situations = situations()

        // ACTION
        val placeNames = situations
            .flatMap { it.publishingActions?.publishingActions.orEmpty() }
            .mapNotNull { it.publishAtScope?.affects?.stopPlaces?.affectedStopPlaces }
            .flatten()
            .mapNotNull { it.placeName }

        // ASSERTION
        assertThat(placeNames).isEqualTo(listOf("Zürich HB", "Zürich Hardbrücke"))
    }

    @Test
    fun `affects keeps stop points absent when only stop places are published`() {
        // GIVEN
        val situations = situations()

        // ACTION
        val stopPoints = situations
            .flatMap { it.publishingActions?.publishingActions.orEmpty() }
            .mapNotNull { it.publishAtScope?.affects?.stopPoints }

        // ASSERTION
        assertThat(stopPoints).isEqualTo(emptyList())
    }
}
