package ch.opentransportdata.ojp.data.dto.converter

import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import ch.opentransportdata.ojp.data.dto.response.tir.LegDto
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encodeToString
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.modules.SerializersModule
import nl.adaptivity.xmlutil.ExperimentalXmlUtilApi
import nl.adaptivity.xmlutil.serialization.XML
import nl.adaptivity.xmlutil.serialization.XmlConfig
import nl.adaptivity.xmlutil.serialization.XmlElement
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.File
import java.time.Duration

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [35])
class DurationSerializationXmlUtilTest {

    object DurationIsoSerializer : KSerializer<Duration> {
        override val descriptor: SerialDescriptor =
            PrimitiveSerialDescriptor("java.time.Duration", PrimitiveKind.STRING)

        override fun deserialize(decoder: Decoder): Duration =
            Duration.parse(decoder.decodeString())

        override fun serialize(encoder: Encoder, value: Duration) =
            encoder.encodeString(value.toString())
    }

    @OptIn(ExperimentalXmlUtilApi::class)
    private fun xml(): XML = XML(
        serializersModule = SerializersModule {
            // Only needed if your LegDto does NOT annotate the field with a serializer.
            contextual(Duration::class, DurationIsoSerializer)
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

    private fun readText(path: String) = File(path).readText()

    @Test
    fun `Duration is not null and is parsed correctly`() {
        // GIVEN
        val xmlText = readText("src/test/resources/converter/duration.xml")

        // ACTION
        val result = xml().decodeFromString<LegDto>(xmlText)

        // ASSERTION
        assertThat(result).isNotNull()
        assertThat(result.duration).isEqualTo(Duration.parse("PT7M"))
    }

    @Test
    fun `Duration is null and is parsed correctly`() {
        // GIVEN
        val xmlText = readText("src/test/resources/converter/no_duration.xml")

        // ACTION
        val result = xml().decodeFromString<LegDto>(xmlText)

        // ASSERTION
        assertThat(result).isNotNull()
        assertThat(result.duration).isEqualTo(null)
    }

    @Serializable
    private data class DurationWrapper(
        @XmlElement(true)
        @Serializable(with = DurationIsoSerializer::class)
        val duration: Duration
    )

    @Test
    fun `Duration to String is correctly formatted`() {
        // GIVEN
        val duration = Duration.parse("PT1H1M")

        // ACTION
        val xmlOut = xml().encodeToString(DurationWrapper(duration))

        // ASSERTION
        assertThat(xmlOut).contains("PT1H1M")
    }
}
