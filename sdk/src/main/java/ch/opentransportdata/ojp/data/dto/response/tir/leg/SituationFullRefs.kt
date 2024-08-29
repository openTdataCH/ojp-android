package ch.opentransportdata.ojp.data.dto.response.tir.leg

import android.os.Parcelable
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Xml
import kotlinx.parcelize.Parcelize

/**
 * Created by Michael Ruppen on 19.08.2024
 */
@Parcelize
@Xml(name = "SituationFullRefs")
data class SituationFullRefs(
    @Element(name = "SituationFullRef")
    val situationFullRefs: List<SituationFullRef>?
) : Parcelable
