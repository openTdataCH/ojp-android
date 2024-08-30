package ch.opentransportdata.ojp.data.dto.response.tir.leg

import android.os.Parcelable
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml
import kotlinx.parcelize.Parcelize
import java.time.Duration
import java.time.LocalDateTime

/**
 * Created by Michael Ruppen on 28.06.2024
 */
@Parcelize
@Xml
data class ServiceTimeDto(
    @PropertyElement(name = "TimetabledTime")
    val timetabledTime: LocalDateTime,
    @PropertyElement(name = "EstimatedTime")
    val estimatedTime: LocalDateTime?,
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