package ch.opentransportdata.ojp.data.dto.response.tir.leg

import android.os.Parcelable
import ch.opentransportdata.ojp.data.dto.OJP_NAME_SPACE
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

/**
 * Created by Michael Ruppen on 19.08.2024
 */

@Parcelize
@Serializable
@XmlSerialName("SituationFullRefs", OJP_NAME_SPACE, "")
data class SituationFullRefs(
    @XmlElement(true)
    @XmlSerialName("SituationFullRef", OJP_NAME_SPACE, "")
    val situationFullRefs: List<SituationFullRef>? = null
) : Parcelable
