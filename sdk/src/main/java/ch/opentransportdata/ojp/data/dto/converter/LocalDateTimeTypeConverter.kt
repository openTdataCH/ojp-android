package ch.opentransportdata.ojp.data.dto.converter

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import timber.log.Timber
import java.time.Instant
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

/**
 * Created by Michael Ruppen on 05.07.2024
 */
class LocalDateTimeSerializer(
    private val zone: ZoneId
) : KSerializer<LocalDateTime> {

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("LocalDateTimeAsInstant", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: LocalDateTime) {
        val instant = value.atZone(zone).toInstant()
        encoder.encodeString(DateTimeFormatter.ISO_INSTANT.format(instant))
    }

    override fun deserialize(decoder: Decoder): LocalDateTime {
        val text = decoder.decodeString()
        return parseToLocalDateTime(text, zone)
    }

    companion object {
        private val ZONED = DateTimeFormatter.ISO_ZONED_DATE_TIME
        private val OFFSET = DateTimeFormatter.ISO_OFFSET_DATE_TIME
        private val INSTANT = DateTimeFormatter.ISO_INSTANT
        private val LOCAL = DateTimeFormatter.ISO_LOCAL_DATE_TIME

        fun parseToLocalDateTime(text: String, zone: ZoneId): LocalDateTime {
            try {
                return ZonedDateTime.parse(text, ZONED)
                    .withZoneSameInstant(zone)
                    .toLocalDateTime()
            } catch (exception: DateTimeParseException) {
                Timber.e("Could not parse ZONED $exception")
            }

            try {
                return OffsetDateTime.parse(text, OFFSET)
                    .atZoneSameInstant(zone)
                    .toLocalDateTime()
            } catch (exception: DateTimeParseException) {
                Timber.e("Could not parse OFFSET $exception")
            }

            try {
                return INSTANT.parse(text, Instant::from)
                    .atZone(zone)
                    .toLocalDateTime()
            } catch (exception: Exception) {
                Timber.e("Could not parse INSTANT $exception")
            }

            try {
                return LocalDateTime.parse(text, LOCAL)
            } catch (exception: Exception) {
                Timber.e("Could not parse LOCAL $exception")
            }
            return LocalDateTime.now()
        }
    }
}
