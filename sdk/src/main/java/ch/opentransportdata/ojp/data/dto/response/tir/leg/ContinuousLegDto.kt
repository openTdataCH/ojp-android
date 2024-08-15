package ch.opentransportdata.ojp.data.dto.response.tir.leg

import android.os.Parcelable
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml
import kotlinx.parcelize.Parcelize
import java.time.Duration

/**
 * Created by Michael Ruppen on 12.07.2024
 */
@Parcelize
@Xml(name = "ContinuousLeg")
data class ContinuousLegDto(
    @Element(name = "LegStart")
    val legStart: LegStartEndDto,
    @Element(name = "LegEnd")
    val legEnd: LegStartEndDto,
//    @Element(name = "Service")
//    val service: ServiceDto,//Todo: create separate ContinuousService
    @PropertyElement(name = "Duration")
    val duration: Duration
) : AbstractLegType(), Parcelable