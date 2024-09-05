package ch.opentransportdata.ojp.data.dto.converter

import ch.opentransportdata.ojp.domain.usecase.Initializer
import com.tickaroo.tikxml.TypeConverter
import timber.log.Timber
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

/**
 * Created by Michael Ruppen on 05.07.2024
 */
internal class LocalDateTimeTypeConverter(private val initializer: Initializer) : TypeConverter<LocalDateTime> {

    override fun read(value: String): LocalDateTime {
        return try {
            val zonedDateTime = ZonedDateTime.parse(value, DateTimeFormatter.ISO_ZONED_DATE_TIME)
            zonedDateTime.withZoneSameInstant(initializer.defaultTimeZone).toLocalDateTime()
        } catch (e: DateTimeParseException) {
            Timber.e(e, "$value is not a valid datetime")
            LocalDateTime.now()
        }
    }

    override fun write(value: LocalDateTime): String {
        return value.toInstant(ZoneOffset.UTC).toString()
    }
}