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
@XmlSerialName("OperatingDays", OJP_NAME_SPACE, "")
data class OperatingDaysDto(
    @XmlElement(true)
    @XmlSerialName("From", OJP_NAME_SPACE, "")
    val from: String,

    @XmlElement(true)
    @XmlSerialName("To", OJP_NAME_SPACE, "")
    val to: String,

    @XmlElement(true)
    @XmlSerialName("Pattern", OJP_NAME_SPACE, "")
    val pattern: String,
) : Parcelable