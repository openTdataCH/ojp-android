package ch.opentransportdata.ojp.data.dto.response.tir.leg

import android.os.Parcelable
import ch.opentransportdata.ojp.data.dto.OJP_NAME_SPACE
import ch.opentransportdata.ojp.data.dto.converter.DurationSerializer
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName
import java.time.Duration

/**
 * Created by Michael Ruppen on 12.07.2024
 */

@Parcelize
@Serializable
@XmlSerialName("ContinuousLeg", OJP_NAME_SPACE, "")
data class ContinuousLegDto(
    @XmlElement(true)
    @XmlSerialName("LegStart", OJP_NAME_SPACE, "")
    val legStart: LegStartEndDto,

    @XmlElement(true)
    @XmlSerialName("LegEnd", OJP_NAME_SPACE, "")
    val legEnd: LegStartEndDto,

    @XmlElement(true)
    @XmlSerialName("Service", OJP_NAME_SPACE, "")
    val service: ContinuousServiceTypeChoiceDto,

    @XmlElement(true)
    @XmlSerialName("Duration", OJP_NAME_SPACE, "")
    @Serializable(with = DurationSerializer::class)
    val duration: Duration
) : AbstractLegType(), Parcelable

fun ContinuousLegDto.minimalCopy(): ContinuousLegDto {
    return ContinuousLegDto(
        legStart = legStart,
        legEnd = legEnd,
        duration = duration,
        service = service
    )
}