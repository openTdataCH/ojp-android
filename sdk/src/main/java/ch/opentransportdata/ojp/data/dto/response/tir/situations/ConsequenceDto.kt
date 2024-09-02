package ch.opentransportdata.ojp.data.dto.response.tir.situations

import android.os.Parcelable
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Xml
import kotlinx.parcelize.Parcelize

/**
 * Created by Michael Ruppen on 28.06.2024
 */
@Parcelize
@Xml(name = "siri:Consequence")
data class ConsequenceDto(
    @Element(name = "siri:Blocking")
    val blocking: BlockingDto?
) : Parcelable
