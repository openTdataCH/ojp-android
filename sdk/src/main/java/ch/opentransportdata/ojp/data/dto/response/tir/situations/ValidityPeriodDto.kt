package ch.opentransportdata.ojp.data.dto.response.tir.situations

import android.os.Parcelable
import ch.opentransportdata.ojp.data.dto.SIRI_NAME_SPACE
import ch.opentransportdata.ojp.data.dto.SIRI_PREFIX
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName
import java.time.LocalDateTime

/**
 * Created by Michael Ruppen on 28.06.2024
 */
@Parcelize
@Serializable
@XmlSerialName("ValidityPeriod", SIRI_NAME_SPACE, SIRI_PREFIX)
data class ValidityPeriodDto(
    @XmlElement(true)
    @XmlSerialName("StartTime", SIRI_NAME_SPACE, SIRI_PREFIX)
    @Contextual
    val startTime: LocalDateTime,

    @XmlElement(true)
    @XmlSerialName("EndTime", SIRI_NAME_SPACE, SIRI_PREFIX)
    @Contextual
    val endTime: LocalDateTime
) : Parcelable