package ch.opentransportdata.ojp.data.dto.converter

import com.tickaroo.tikxml.TypeConverter
import timber.log.Timber
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

/**
 * Created by Michael Ruppen on 05.07.2024
 */
internal class LocalDateTimeTypeConverter : TypeConverter<LocalDateTime> {

    override fun read(value: String): LocalDateTime {
        return try {
            val zonedDateTime = ZonedDateTime.parse(value, DateTimeFormatter.ISO_ZONED_DATE_TIME)
            val zoneId = ZoneId.of("UTC")
            zonedDateTime.withZoneSameInstant(zoneId).toLocalDateTime()
        } catch (e: DateTimeParseException) {
            Timber.e(e, "$value is not a valid datetime")
            LocalDateTime.now()
        }
    }

    override fun write(value: LocalDateTime): String {
        return value.toInstant(ZoneOffset.UTC).toString()
    }
}