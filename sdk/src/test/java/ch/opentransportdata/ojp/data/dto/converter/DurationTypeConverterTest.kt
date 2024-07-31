package ch.opentransportdata.ojp.data.dto.converter

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import ch.opentransportdata.ojp.TestUtils
import ch.opentransportdata.ojp.data.dto.response.tir.LegDto
import ch.opentransportdata.ojp.domain.model.TransferType
import com.tickaroo.tikxml.TikXml
import org.junit.Test
import java.time.Duration

/**
 * Created by Michael Ruppen on 31.07.2024
 */
class DurationTypeConverterTest {

    private val tikXml = TikXml.Builder()
        .addTypeConverter(Duration::class.java, DurationTypeConverter())
        .addTypeConverter(TransferType::class.java, TransferTypeConverter())
        .build()

    @Test
    fun `Duration is not null and is parsed correctly`() {
        // GIVEN
        val xmlFile = "src/test/resources/converter/duration.xml"
        val bufferedSource = TestUtils().readXmlFile(xmlFile)

        // ACTION
        val result = tikXml.read<LegDto>(bufferedSource, LegDto::class.java)

        // ASSERTION
        assertThat(result).isNotNull()
        assertThat(result.duration).isEqualTo(Duration.parse("PT7M"))
    }

    @Test
    fun `Duration is null and is parsed correctly`() {
        // GIVEN
        val xmlFile = "src/test/resources/converter/no_duration.xml"
        val bufferedSource = TestUtils().readXmlFile(xmlFile)

        // ACTION
        val result = tikXml.read<LegDto>(bufferedSource, LegDto::class.java)

        // ASSERTION
        assertThat(result).isNotNull()
        assertThat(result.duration).isEqualTo(null)
    }

    @Test
    fun `Duration to String is correctly formatted`() {
        // GIVEN
        val duration = Duration.parse("PT1H1M")

        // ACTION
        val result = DurationTypeConverter().write(duration)

        // ASSERTION
        assertThat(result).isEqualTo("PT1H1M")
    }
}