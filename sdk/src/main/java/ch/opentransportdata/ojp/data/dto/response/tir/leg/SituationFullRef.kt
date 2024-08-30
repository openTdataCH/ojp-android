package ch.opentransportdata.ojp.data.dto.response.tir.leg

import android.os.Parcelable
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml
import kotlinx.parcelize.Parcelize

/**
 * Created by Deniz Kalem on 16.08.2024
 */
@Parcelize
@Xml(name = "SituationFullRef")
data class SituationFullRef(
    @PropertyElement(name = "siri:ParticipantRef")
    val participantRef: String,
    @PropertyElement(name = "siri:SituationNumber")
    val situationNumber: String
) : Parcelable