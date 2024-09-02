package ch.opentransportdata.ojp.data.dto.response.tir.situations

import android.os.Parcelable
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

/**
 * Created by Michael Ruppen on 28.06.2024
 */
@Parcelize
@Xml(name = "siri:ValidityPeriod")
data class ValidityPeriodDto(
    @PropertyElement(name = "siri:StartTime")
    val startTime: LocalDateTime,
    @PropertyElement(name = "siri:EndTime")
    val endTime: LocalDateTime
) : Parcelable
