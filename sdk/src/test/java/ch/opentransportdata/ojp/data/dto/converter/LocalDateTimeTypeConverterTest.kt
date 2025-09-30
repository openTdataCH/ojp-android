package ch.opentransportdata.ojp.data.dto.converter

import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.isEqualTo
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import nl.adaptivity.xmlutil.serialization.XML
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName
import org.junit.Test
import java.time.LocalDateTime

class LocalDateTimeSerializerXmlUtilTest {

    private val xml = XML {}

    @Serializable
    @XmlSerialName("Root", "", "")
    private data class Root(
        @XmlElement(true)
        @XmlSerialName("When", "", "")
        @Serializable(with = LocalDateTimeSerializer::class)
        val whenLocal: LocalDateTime
    )

    @Test
    fun `parses ISO local datetime without offset`() {
        val s = "2024-09-04T13:29:24.1819927"
        val result = xml.decodeFromString<Root>("<Root><When>$s</When></Root>")
        assertThat(result.whenLocal).isEqualTo(LocalDateTime.parse(s))
    }

    @Test
    fun `parses value with offset and ignores the offset`() {
        val s = "2024-09-04T13:29:24.1819927+02:00"
        val result = xml.decodeFromString<Root>("<Root><When>$s</When></Root>")
        assertThat(result.whenLocal).isEqualTo(
            LocalDateTime.parse("2024-09-04T13:29:24.1819927")
        )
    }

    @Test
    fun `parses Zulu time and ignores the Z`() {
        val s = "2024-09-04T07:40:00Z"
        val result = xml.decodeFromString<Root>("<Root><When>$s</When></Root>")
        assertThat(result.whenLocal).isEqualTo(
            LocalDateTime.parse("2024-09-04T07:40:00")
        )
    }

    @Test
    fun `serializes to ISO local datetime without zone`() {
        val value = LocalDateTime.parse("2024-09-05T15:02:49.258568")
        val xmlOut = xml.encodeToString(Root.serializer(), Root(value))
        assertThat(xmlOut).contains("<When>2024-09-05T15:02:49.258568</When>")
    }

    @Test
    fun `round-trip local datetime`() {
        val original = LocalDateTime.parse("2024-12-01T08:09:10.123456789")
        val encoded = xml.encodeToString(Root.serializer(), Root(original))
        val decoded = xml.decodeFromString<Root>(encoded)
        assertThat(decoded.whenLocal).isEqualTo(original)
    }
}