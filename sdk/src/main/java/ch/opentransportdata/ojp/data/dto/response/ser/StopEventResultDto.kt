package ch.opentransportdata.ojp.data.dto.response.ser

import android.os.Parcelable
import ch.opentransportdata.ojp.data.dto.OJP_NAME_SPACE
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

/**
 * Created by Deniz Kalem on 20.05.2026
 */
@Parcelize
@Serializable
@XmlSerialName("StopEventResult", OJP_NAME_SPACE, "")
data class StopEventResultDto(
    @XmlElement(true)
    @XmlSerialName("Id", OJP_NAME_SPACE, "")
    val id: String,

    @XmlElement(true)
    val stopEvent: StopEventDto,
) : Parcelable