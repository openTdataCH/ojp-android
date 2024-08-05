package ch.opentransportdata.ojp.data.dto.converter

import com.tickaroo.tikxml.TypeConverter
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

/**
 * Created by Michael Ruppen on 05.07.2024
 */
class LocalDateTimeTypeConverter : TypeConverter<LocalDateTime> {
    override fun read(value: String): LocalDateTime {
        val formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME // Use ISO_ZONED_DATE_TIME
        val zonedDateTime = ZonedDateTime.parse(value, formatter)

        // Convert to LocalDateTime in your local time zone
        val localZoneId = ZoneId.of("UTC")
        return zonedDateTime.withZoneSameInstant(localZoneId).toLocalDateTime()
    }

    override fun write(value: LocalDateTime): String {
        return value.toInstant(ZoneOffset.UTC).toString()
    }
}

