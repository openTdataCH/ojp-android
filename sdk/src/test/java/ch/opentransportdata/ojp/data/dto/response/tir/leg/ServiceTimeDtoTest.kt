package ch.opentransportdata.ojp.data.dto.response.tir.leg

import assertk.assertions.isEqualTo
import org.junit.Test
import java.time.Duration
import java.time.LocalDateTime

/**
 * Created by Michael Ruppen on 29.08.2024
 */
class ServiceTimeDtoTest {

    @Test
    fun `EstimatedTime and TimetabledTime are the same, no delay`() {
        // GIVEN
        val currentTime = LocalDateTime.now()
        val serviceTime = ServiceTimeDto(
            timetabledTime = currentTime,
            estimatedTime = currentTime
        )

        // ASSERTION
        assertk.assertThat(serviceTime.hasDelay).isEqualTo(false)
        assertk.assertThat(serviceTime.delay).isEqualTo(Duration.ZERO)
        assertk.assertThat(serviceTime.mergedTime).isEqualTo(currentTime)
    }

    @Test
    fun `EstimatedTime is null, no delay`() {
        // GIVEN
        val currentTime = LocalDateTime.now()
        val serviceTime = ServiceTimeDto(
            timetabledTime = currentTime,
            estimatedTime = null
        )

        // ASSERTION
        assertk.assertThat(serviceTime.hasDelay).isEqualTo(false)
        assertk.assertThat(serviceTime.delay).isEqualTo(Duration.ZERO)
        assertk.assertThat(serviceTime.mergedTime).isEqualTo(currentTime)
    }

    @Test
    fun `EstimatedTime is 20 seconds later, not a delay but has 20 seconds as delay`() {
        // GIVEN
        val currentTime = LocalDateTime.now()
        val estimatedTime = currentTime.plusSeconds(20)
        val serviceTime = ServiceTimeDto(
            timetabledTime = currentTime,
            estimatedTime = estimatedTime
        )

        // ACTION
        val delay = Duration.parse("PT20S")

        // ASSERTION
        assertk.assertThat(serviceTime.hasDelay).isEqualTo(false)
        assertk.assertThat(serviceTime.delay).isEqualTo(delay)
        assertk.assertThat(serviceTime.mergedTime).isEqualTo(estimatedTime)
    }

    @Test
    fun `EstimatedTime is 59 seconds later, not a delay but has 59 seconds as delay`() {
        // GIVEN
        val currentTime = LocalDateTime.now()
        val estimatedTime = currentTime.plusSeconds(59)
        val serviceTime = ServiceTimeDto(
            timetabledTime = currentTime,
            estimatedTime = estimatedTime
        )

        // ACTION
        val delay = Duration.parse("PT59S")

        // ASSERTION
        assertk.assertThat(serviceTime.hasDelay).isEqualTo(false)
        assertk.assertThat(serviceTime.delay).isEqualTo(delay)
        assertk.assertThat(serviceTime.mergedTime).isEqualTo(estimatedTime)
    }

    @Test
    fun `EstimatedTime is 18seconds before to TimetabledTime, not a delay but has -18 seconds as delay`() {
        // GIVEN
        val currentTime = LocalDateTime.now()
        val estimatedTime = currentTime.minusSeconds(18)
        val serviceTime = ServiceTimeDto(
            timetabledTime = currentTime,
            estimatedTime = estimatedTime
        )

        // ACTION
        val delay = Duration.parse("PT-18S")

        // ASSERTION
        assertk.assertThat(serviceTime.hasDelay).isEqualTo(false)
        assertk.assertThat(serviceTime.delay).isEqualTo(delay)
        assertk.assertThat(serviceTime.mergedTime).isEqualTo(estimatedTime)
    }

    @Test
    fun `EstimatedTime is 12 minutes before to TimetabledTime, not a delay but has -12 minutes as delay`() {
        // GIVEN
        val currentTime = LocalDateTime.now()
        val estimatedTime = currentTime.minusMinutes(12)
        val serviceTime = ServiceTimeDto(
            timetabledTime = currentTime,
            estimatedTime = estimatedTime
        )

        // ACTION
        val delay = Duration.parse("PT-12M")

        // ASSERTION
        assertk.assertThat(serviceTime.hasDelay).isEqualTo(false)
        assertk.assertThat(serviceTime.delay).isEqualTo(delay)
        assertk.assertThat(serviceTime.mergedTime).isEqualTo(estimatedTime)
    }

    @Test
    fun `EstimatedTime is 1 hour later, delay and has 60 minutes as delay`() {
        // GIVEN
        val currentTime = LocalDateTime.now()
        val estimatedTime = currentTime.plusHours(1)
        val serviceTime = ServiceTimeDto(
            timetabledTime = currentTime,
            estimatedTime = estimatedTime
        )

        // ACTION
        val delay = Duration.parse("PT1H")

        // ASSERTION
        assertk.assertThat(serviceTime.hasDelay).isEqualTo(true)
        assertk.assertThat(serviceTime.delay).isEqualTo(delay)
        assertk.assertThat(serviceTime.mergedTime).isEqualTo(estimatedTime)
    }

    @Test
    fun `EstimatedTime is 4 minutes later, delay and has 4 minutes as delay`() {
        // GIVEN
        val currentTime = LocalDateTime.now()
        val estimatedTime = currentTime.plusMinutes(4)
        val serviceTime = ServiceTimeDto(
            timetabledTime = currentTime,
            estimatedTime = estimatedTime
        )

        // ACTION
        val delay = Duration.parse("PT240S")

        // ASSERTION
        assertk.assertThat(serviceTime.hasDelay).isEqualTo(true)
        assertk.assertThat(serviceTime.delay).isEqualTo(delay)
        assertk.assertThat(serviceTime.mergedTime).isEqualTo(estimatedTime)
    }
}