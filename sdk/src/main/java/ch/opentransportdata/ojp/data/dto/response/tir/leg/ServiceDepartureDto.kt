package ch.opentransportdata.ojp.data.dto.response.tir.leg

import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml
import java.time.Instant

/**
 * Created by Michael Ruppen on 28.06.2024
 */
@Xml(name = "ServiceDeparture")
data class ServiceDepartureDto(
    @PropertyElement(name = "TimetabledTime")
    val timetabledTime: String,
    @PropertyElement(name = "EstimatedTime")
    val estimatedTime: String?,
) {

    val timetabledTimeInstant: Instant
        get() = Instant.parse(timetabledTime)

    val estimatedTimeInstant: Instant
        get() = Instant.parse(estimatedTime)

    private val diff: Long
        get() = estimatedTimeInstant.toEpochMilli() - timetabledTimeInstant.toEpochMilli()

    //todo: delay calculation
}
