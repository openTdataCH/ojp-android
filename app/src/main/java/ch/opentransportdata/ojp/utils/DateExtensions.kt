package ch.opentransportdata.ojp.utils

import org.joda.time.DateTimeZone
import org.joda.time.Instant
import org.joda.time.LocalDate
import org.joda.time.LocalDateTime

/**
 * Created by Michael Ruppen on 08.04.2024
 */

fun LocalDateTime.toInstantString(): String {
    return this.toDateTime(DateTimeZone.UTC).toInstant().toString()
}
fun LocalDate.toInstantString(): String {
    return this.toDateTimeAtStartOfDay(DateTimeZone.UTC).toDateTime().toInstant().toString()
}

fun String.instantToLocalDate(): LocalDate {
    return Instant.parse(this).toDateTime().toLocalDate()
}

fun String.instantToLocalDateTime(): LocalDateTime {
    return Instant.parse(this).toDateTime().toLocalDateTime()
}