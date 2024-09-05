package ch.opentransportdata.ojp.data.dto.converter

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotEqualTo
import assertk.assertions.isNotNull
import ch.opentransportdata.ojp.TestUtils
import ch.opentransportdata.ojp.data.dto.response.delivery.TripDeliveryDto
import ch.opentransportdata.ojp.domain.usecase.Initializer
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime
import java.time.ZoneId

/**
 * Created by Michael Ruppen on 05.09.2024
 */
class LocalDateTimeTypeConverterTest {

    private lateinit var dateTimeConverter: LocalDateTimeTypeConverter
    private val initializer = Initializer()

    @Before
    fun setUp() {
        initializer.defaultTimeZone = ZoneId.of("Europe/Zurich")
        dateTimeConverter = LocalDateTimeTypeConverter(initializer)
    }

    @Test
    fun `Date time string has format +2,00 is parsed correctly to given timeZone EuropeZurich`() {
        // GIVEN
        val timeString = "2024-09-04T13:29:24.1819927+02:00"

        // ACTION
        val result = dateTimeConverter.read(timeString)

        // ASSERTION
        assertThat(result).isNotEqualTo(LocalDateTime.now())
        assertThat(result.hour).isEqualTo(13)
    }

    @Test
    fun `Date time string has format +1,00 is parsed correctly to given timeZone EuropeZurich`() {
        // GIVEN
        val timeString = "2024-09-04T13:29:24.1819927+01:00"

        // ACTION
        val result = dateTimeConverter.read(timeString)

        // ASSERTION
        assertThat(result).isNotEqualTo(LocalDateTime.now())
        assertThat(result.hour).isEqualTo(14)
    }

    @Test
    fun `Date time string has format +0,00 is parsed correctly to given timeZone EuropeZurich`() {
        // GIVEN
        val timeString = "2024-09-04T13:29:24.1819927+00:00"

        // ACTION
        val result = dateTimeConverter.read(timeString)

        // ASSERTION
        assertThat(result).isNotEqualTo(LocalDateTime.now())
        assertThat(result.hour).isEqualTo(15)
    }

    @Test
    fun `Date time string in wrong format falls back to LocalDateTimeNow()`() {
        // GIVEN
        val timeString = "2024-09-04T13:29:24.1819927"
        val currentTime = LocalDateTime.now()

        // ACTION
        val result = dateTimeConverter.read(timeString)

        // ASSERTION
        assertThat(result.hour).isEqualTo(currentTime.hour)
        assertThat(result.minute).isEqualTo(currentTime.minute)
    }

    @Test
    fun `Date time string as zulu time is parsed correctly to given timeZone EuropeZurich`() {
        // GIVEN
        val timeString = "2024-09-04T07:40:00Z"

        // ACTION
        val result = dateTimeConverter.read(timeString)

        // ASSERTION
        assertThat(result).isNotEqualTo(LocalDateTime.now())
        assertThat(result.hour).isEqualTo(9)
    }

    @Test
    fun `LocalDateTime to string parsed correctly to zulu time`() {
        // GIVEN
        val timeString = "2024-09-05T15:02:49.258568Z"
        val time = dateTimeConverter.read(timeString)

        // ACTION
        val result = dateTimeConverter.write(time)

        // ASSERTION
        assertThat(result).isNotEqualTo("2024-09-05T15:02:49.258568Z")
    }
}