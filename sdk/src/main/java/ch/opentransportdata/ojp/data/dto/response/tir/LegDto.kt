package ch.opentransportdata.ojp.data.dto.response.tir

import android.os.Parcelable
import ch.opentransportdata.ojp.data.dto.response.PlacesDto
import ch.opentransportdata.ojp.data.dto.response.tir.leg.AbstractLegType
import ch.opentransportdata.ojp.data.dto.response.tir.leg.ContinuousLegDto
import ch.opentransportdata.ojp.data.dto.response.tir.leg.TimedLegDto
import ch.opentransportdata.ojp.data.dto.response.tir.leg.TransferLegDto
import ch.opentransportdata.ojp.data.dto.response.tir.leg.minimalCopy
import ch.opentransportdata.ojp.data.dto.response.tir.leg.replaceWithParentRef
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml
import kotlinx.parcelize.Parcelize
import java.time.Duration

/**
 * Created by Michael Ruppen on 28.06.2024
 */
@Parcelize
@Xml(name = "Leg")
data class LegDto(
    @PropertyElement(name = "Id")
    val id: String,
    @PropertyElement(name = "Duration")
    val duration: Duration?,
    @Element(name = "TimedLeg")
    val timedLeg: TimedLegDto? = null,
    @Element(name = "TransferLeg")
    val transferLeg: TransferLegDto? = null,
    @Element(name = "ContinuousLeg")
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