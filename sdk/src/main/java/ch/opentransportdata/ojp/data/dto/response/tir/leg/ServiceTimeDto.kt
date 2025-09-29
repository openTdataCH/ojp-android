package ch.opentransportdata.ojp.data.dto.response.tir.leg

import android.os.Parcelable
import ch.opentransportdata.ojp.data.dto.OJP_NAME_SPACE
import ch.opentransportdata.ojp.data.dto.converter.LocalDateTimeSerializer
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName
import java.time.Duration
import java.time.LocalDateTime

/**
 * Created by Michael Ruppen on 28.06.2024
 */
@Parcelize
@Serializable
data class ServiceTimeDto(
    @XmlElement(true)
    @XmlSerialName("TimetabledTime", OJP_NAME_SPACE, "")
    @Serializable(with = LocalDateTimeSerializer::class)
    val timetabledTime: LocalDateTime,

    @XmlElement(true)
    @XmlSerialName("EstimatedTime", OJP_NAME_SPACE, "")
    @Serializable(with = LocalDateTimeSerializer::class)
    val estimatedTime: LocalDateTime? = null
) : Parcelable {

    /**
     * Only considered a delay if duration between estimated and timetabled time is >= 1 minute
     */
    val hasDelay: Boolean
        get() = estimatedTime != null && delay.toMinutes() > 0

    val delay: Duration
        get() = if (estimatedTime != null) Duration.between(timetabledTime, estimatedTime) else Duration.ZERO

    val mergedTime: LocalDateTime
        get() = estimatedTime ?: timetabledTime
}