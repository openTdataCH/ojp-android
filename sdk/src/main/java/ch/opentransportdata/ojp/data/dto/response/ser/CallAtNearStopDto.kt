package ch.opentransportdata.ojp.data.dto.response.ser

import android.os.Parcelable
import ch.opentransportdata.ojp.data.dto.OJP_NAME_SPACE
import ch.opentransportdata.ojp.data.dto.response.tir.CallAtStopDto
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

/**
 * Created by Deniz Kalem on 20.05.2026
 */
@Parcelize
@Serializable
data class CallAtNearStopDto(
    @XmlElement(true)
    @XmlSerialName("CallAtStop", OJP_NAME_SPACE, "")
    val callAtStop: CallAtStopDto,
) : Parcelable