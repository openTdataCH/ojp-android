package ch.opentransportdata.ojp.data.dto.response.tir.situations

import android.os.Parcelable
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Xml
import kotlinx.parcelize.Parcelize

/**
 * Created by Deniz Kalem on 16.08.2024
 */
@Parcelize
@Xml(name = "siri:SituationFullRef")
data class SituationFullRef(
    @Element(name = "siri:ParticipantRef")
    val participantRef: String,
    @Element(name = "siri:SituationNumber")
    val situationNumber: String
) : Parcelable