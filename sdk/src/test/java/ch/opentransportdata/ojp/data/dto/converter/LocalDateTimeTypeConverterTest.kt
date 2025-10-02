package ch.opentransportdata.ojp.data.dto.converter

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotEqualTo
import kotlinx.serialization.json.Json
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime
import java.time.ZoneId

class LocalDateTimeSerializerXmlUtilTest {

    private val zone = ZoneId.of("Europe/Zurich")
    private lateinit var serializer: LocalDateTimeSerializer

    @Before
    fun setUp() {
        serializer = LocalDateTimeSerializer(zone)
    }

    @Test
    fun `Date time string has format +2,00 is parsed correctly to given timeZone EuropeZurich`() {
        // GIVEN
        val timeString = "2024-09-04T13:29:24.1819927+02:00"

        // ACTION
        val result = LocalDateTimeSerializer.parseToLocalDateTime(timeString, zone)

        // ASSERTION
        assertThat(result).isNotEqualTo(LocalDateTime.now(zone))
        assertThat(result.hour).isEqualTo(13)
    }

    @Test
    fun `Date time string has format +1,00 is parsed correctly to given timeZone EuropeZurich`() {
        // GIVEN
        val timeString = "2024-09-04T13:29:24.1819927+01:00"

        // ACTION
        val result = LocalDateTimeSerializer.parseToLocalDateTime(timeString, zone)

        // ASSERTION
        assertThat(result).isNotEqualTo(LocalDateTime.now(zone))
        assertThat(result.hour).isEqualTo(14)
    }

    @Test
    fun `Date time string has format +0,00 is parsed correctly to given timeZone EuropeZurich`() {
        // GIVEN
        val timeString = "2024-09-04T13:29:24.1819927+00:00"

        // ACTION
        val result = LocalDateTimeSerializer.parseToLocalDateTime(timeString, zone)

        // ASSERTION
        assertThat(result).isNotEqualTo(LocalDateTime.now(zone))
        assertThat(result.hour).isEqualTo(15)
    }

    @Test
    fun `Bare local date time is treated as target-zone local time`() {
        // GIVEN
        val timeString = "2024-09-04T13:29:24.1819927"

        // ACTION
        val result = LocalDateTimeSerializer.parseToLocalDateTime(timeString, zone)

        // ASSERTION
        assertThat(result).isEqualTo(LocalDateTime.parse(timeString))
    }

    @Test
    fun `Date time string as zulu time is parsed correctly to given timeZone EuropeZurich`() {
        // GIVEN
        val timeString = "2024-09-04T07:40:00Z"

        // ACTION
        val result = LocalDateTimeSerializer.parseToLocalDateTime(timeString, zone)

        // ASSERTION
        assertThat(result).isNotEqualTo(LocalDateTime.now(zone))
        assertThat(result.hour).isEqualTo(9)
    }

    @Test
    fun `LocalDateTime to string parsed correctly to zulu time`() {
        // GIVEN should be identical
        val timeString = "2024-09-05T15:02:49.258568Z"

        // ACTION
        val decoded = Json.decodeFromString(serializer, "\"$timeString\"")
        val reEncoded = Json.encodeToString(serializer, decoded).trim('"')

        // ASSERTION
        assertThat(reEncoded).isEqualTo(timeString)
    }
}