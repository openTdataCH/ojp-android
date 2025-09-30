package ch.opentransportdata.ojp.data.dto.response.tir.leg

import android.os.Parcelable
import ch.opentransportdata.ojp.data.dto.OJP_NAME_SPACE
import ch.opentransportdata.ojp.data.dto.SIRI_NAME_SPACE
import ch.opentransportdata.ojp.data.dto.SIRI_PREFIX
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

/**
 * Created by Deniz Kalem on 16.08.2024
 */

@Parcelize
@Serializable
@XmlSerialName("SituationFullRef", OJP_NAME_SPACE, "")
data class SituationFullRef(
    @XmlElement(true)
    @XmlSerialName("ParticipantRef", SIRI_NAME_SPACE, SIRI_PREFIX)
    val participantRef: String,

    @XmlElement(true)
    @XmlSerialName("SituationNumber", SIRI_NAME_SPACE, SIRI_PREFIX)
    val situationNumber: String
) : Parcelable