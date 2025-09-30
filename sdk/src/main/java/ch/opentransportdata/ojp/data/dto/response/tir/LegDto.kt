package ch.opentransportdata.ojp.data.dto.response.tir

import android.os.Parcelable
import ch.opentransportdata.ojp.data.dto.OJP_NAME_SPACE
import ch.opentransportdata.ojp.data.dto.converter.DurationSerializer
import ch.opentransportdata.ojp.data.dto.response.PlacesDto
import ch.opentransportdata.ojp.data.dto.response.tir.leg.AbstractLegType
import ch.opentransportdata.ojp.data.dto.response.tir.leg.ContinuousLegDto
import ch.opentransportdata.ojp.data.dto.response.tir.leg.TimedLegDto
import ch.opentransportdata.ojp.data.dto.response.tir.leg.TransferLegDto
import ch.opentransportdata.ojp.data.dto.response.tir.leg.minimalCopy
import ch.opentransportdata.ojp.data.dto.response.tir.leg.replaceWithParentRef
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName
import java.time.Duration

/**
 * Created by Michael Ruppen on 28.06.2024
 */
@Parcelize
@Serializable
@XmlSerialName("Leg", OJP_NAME_SPACE, "")
data class LegDto(
    @XmlElement(true)
    @XmlSerialName("Id", OJP_NAME_SPACE, "")
    val id: String,

    @XmlElement(true)
    @XmlSerialName("Duration", OJP_NAME_SPACE, "")
    @Serializable(with = DurationSerializer::class)
    val duration: Duration? = null,

    @XmlElement(true)
    @XmlSerialName("TimedLeg", OJP_NAME_SPACE, "")
    val timedLeg: TimedLegDto? = null,

    @XmlElement(true)
    @XmlSerialName("TransferLeg", OJP_NAME_SPACE, "")
    val transferLeg: TransferLegDto? = null,

    @XmlElement(true)
    @XmlSerialName("ContinuousLeg", OJP_NAME_SPACE, "")
    val continuousLeg: ContinuousLegDto? = null
) : Parcelable {

    val legType: AbstractLegType?
        get() = timedLeg ?: transferLeg ?: continuousLeg
}

fun LegDto.minimalCopy(): LegDto {
    return LegDto(
        id = id,
        duration = duration,
        timedLeg = timedLeg?.minimalCopy(),
        transferLeg = transferLeg,
        continuousLeg = continuousLeg?.minimalCopy()
    )
}

fun LegDto.replaceWithParentRef(places: PlacesDto): LegDto {
    return this.copy(
        timedLeg = timedLeg?.replaceWithParentRef(places),
        transferLeg = transferLeg,
        continuousLeg = continuousLeg
    )
}