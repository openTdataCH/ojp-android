package ch.opentransportdata.ojp.data.dto.response.tir.leg

import android.os.Parcelable
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Xml
import kotlinx.parcelize.Parcelize

/**
 * Created by Michael Ruppen on 28.06.2024
 */
@Parcelize
@Xml(name = "LegTrack")
data class LegTrackDto(
    @Element(name = "TrackSection")
    val trackSection: List<TrackSectionDto>
) : Parcelable