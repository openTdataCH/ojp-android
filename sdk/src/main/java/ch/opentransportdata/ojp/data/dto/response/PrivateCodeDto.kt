package ch.opentransportdata.ojp.data.dto.response

import android.os.Parcelable
import ch.opentransportdata.ojp.data.dto.OJP_NAME_SPACE
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

/**
 * Created by Michael Ruppen on 26.04.2024
 *
 * Serializable and Parcelize annotation is both needed for compose navigation with custom types
 */
@Parcelize
@Serializable
@XmlSerialName("PrivateCode", OJP_NAME_SPACE, "")
data class PrivateCodeDto(
    @XmlElement(true)
    @XmlSerialName("System", OJP_NAME_SPACE, "")
    val system: String,

    @XmlElement(true)
    @XmlSerialName("Value", OJP_NAME_SPACE, "")
    val value: String
) : Parcelable
