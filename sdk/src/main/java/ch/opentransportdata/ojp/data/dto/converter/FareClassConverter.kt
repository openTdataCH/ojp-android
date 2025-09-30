package ch.opentransportdata.ojp.data.dto.converter

import ch.opentransportdata.ojp.domain.model.FareClass
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder


/**
 * Created by Deniz Kalem on 02.07.2025
 */
object FareClassSerializer : KSerializer<FareClass> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("FareClass", PrimitiveKind.STRING)

    private val toWire = mapOf(
        FareClass.UNKNOWN to "unknown",
        FareClass.FIRST_CLASS to "firstClass",
        FareClass.SECOND_CLASS to "secondClass "
    )

    private val fromWire = mapOf(
        "unknown" to FareClass.UNKNOWN,
        "firstClass" to FareClass.FIRST_CLASS,
        "secondClass " to FareClass.SECOND_CLASS,
        "secondClass" to FareClass.SECOND_CLASS
    )

    override fun serialize(encoder: Encoder, value: FareClass) {
        encoder.encodeString(toWire[value] ?: "unknown")
    }

    override fun deserialize(decoder: Decoder): FareClass {
        val raw = decoder.decodeString()
        return fromWire[raw] ?: fromWire[raw.trim()] ?: FareClass.UNKNOWN
    }
}