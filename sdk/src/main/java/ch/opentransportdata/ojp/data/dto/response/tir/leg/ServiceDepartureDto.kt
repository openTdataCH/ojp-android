package ch.opentransportdata.ojp.data.dto.response.tir.leg

import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml
import java.time.Duration
import java.time.LocalDateTime

/**
 * Created by Michael Ruppen on 28.06.2024
 */
@Xml(name = "ServiceDeparture")
data class ServiceDepartureDto(
    @PropertyElement(name = "TimetabledTime")
    val timetabledTime: LocalDateTime,
    @PropertyElement(name = "EstimatedTime")
    val estimatedTime: LocalDateTime?,
) {

    val hasDelay: Boolean
        get() = estimatedTime != null && delay.toMinutes() > 0

    val delay: Duration
        get() = Duration.between(timetabledTime, estimatedTime)
}
