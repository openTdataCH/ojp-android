package ch.opentransportdata.ojp.data.dto.response.tir.situations

import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml
import java.time.LocalDateTime

/**
 * Created by Michael Ruppen on 28.06.2024
 */
@Xml(name = "siri:ValidityPeriod")
data class ValidityPeriodDto(
    @PropertyElement(name = "siri:StartTime")
    val startTime: LocalDateTime,
    @PropertyElement(name = "siri:EndTime")
    val endTime: LocalDateTime
)
