package ch.opentransportdata.ojp.data.dto.adapter

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isGreaterThan
import assertk.assertions.isNotNull
import ch.opentransportdata.ojp.TestUtils
import ch.opentransportdata.ojp.data.dto.converter.LocalDateTimeTypeConverter
import ch.opentransportdata.ojp.data.dto.converter.PtModeTypeConverter
import ch.opentransportdata.ojp.data.dto.response.ServiceDeliveryDto
import ch.opentransportdata.ojp.data.dto.response.delivery.LocationInformationDeliveryDto
import ch.opentransportdata.ojp.domain.model.PtMode
import com.tickaroo.tikxml.TikXml
import okio.Buffer
import org.junit.Assert.assertThrows
import org.junit.Test

/**
 * Created by Michael Ruppen on 17.05.2024
 */
class ServiceDeliveryAdapterTest {

    private val tikXml = TikXml.Builder()
        .addTypeConverter(PtMode::class.java, PtModeTypeConverter())
        .addTypeConverter(java.time.LocalDateTime::class.java, LocalDateTimeTypeConverter())
        .build()

    @Test
    fun `Delivery type LocationInformation, parsing successful`() {
        // GIVEN
        val xmlFile = "src/test/resources/adapter/service/location_information_delivery.xml"
        val bufferedSource = TestUtils().readXmlFile(xmlFile)

        // ACTION
        val result = tikXml.read<ServiceDeliveryDto>(bufferedSource, ServiceDeliveryDto::class.java)

        // ASSERTION
        assertThat(result).isNotNull()

        println("Result parsed is: ${result.responseTimestamp}")

        assertThat(result.responseTimestamp.toString()).isEqualTo("2024-04-12T13:56:49.218851300")
        assertThat(result.producerRef).isEqualTo("MENTZ")
        assertThat(result.ojpDelivery.javaClass).isEqualTo(LocationInformationDeliveryDto::class.java)

        val delivery = result.ojpDelivery as LocationInformationDeliveryDto
        assertThat(delivery.placeResults).isNotNull()
        assertThat(delivery.placeResults.isNullOrEmpty()).isFalse()
    }

    @Test
    fun `No delivery object available, parsing failed`() {
        // GIVEN
        val xmlFile = "src/test/resources/adapter/service/no_delivery.xml"
        val bufferedSource = TestUtils().readXmlFile(xmlFile)

        // ACTION
        val result: () -> Unit = { tikXml.read<ServiceDeliveryDto>(bufferedSource, ServiceDeliveryDto::class.java) }

        // ASSERTION
        assertThrows(NullPointerException::class.java, result)
    }

    @Test
    fun `Encode ServiceDelivery to string, encoding successful`() {
        // GIVEN
        val xmlFile = "src/test/resources/adapter/service/location_information_delivery.xml"
        val bufferedSource = TestUtils().readXmlFile(xmlFile)
        val result = tikXml.read<ServiceDeliveryDto>(bufferedSource, ServiceDeliveryDto::class.java)
        assertThat(result).isNotNull()

        // ACTION
        val buffer = Buffer()
        assertThat(buffer.size).isEqualTo(0)
        tikXml.write(buffer, result)

        // ASSERTION
        assertThat(buffer.size).isGreaterThan(0)
    }
}