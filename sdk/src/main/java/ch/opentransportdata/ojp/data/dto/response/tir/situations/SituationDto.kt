package ch.opentransportdata.ojp.data.dto.response.tir.situations

import android.os.Parcelable
import ch.opentransportdata.ojp.data.dto.OJP_NAME_SPACE
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

/**
 * Created by Michael Ruppen on 28.06.2024
 */
@Parcelize
@Serializable
@XmlSerialName("Situations", OJP_NAME_SPACE, "")
data class SituationDto(
    @XmlElement(true)
    @XmlSerialName("PtSituation", OJP_NAME_SPACE, "")
    val ptSituation: List<PtSituationDto>? = null
) : Parcelable