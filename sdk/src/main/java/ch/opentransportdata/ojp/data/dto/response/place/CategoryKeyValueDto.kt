package ch.opentransportdata.ojp.data.dto.response.place

import android.os.Parcelable
import ch.opentransportdata.ojp.data.dto.OJP_NAME_SPACE
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

/**
 * Created by Deniz Kalem on 02.07.2026
 *
 */
@Parcelize
@Serializable
@XmlSerialName("POIAdditionalInformation", OJP_NAME_SPACE, "")
data class CategoryKeyValueDto(
    @XmlElement(true)
    @XmlSerialName("Key", OJP_NAME_SPACE, "")
    val key: String,

    @XmlElement(true)
    @XmlSerialName("Value", OJP_NAME_SPACE, "")
    val value: String
) : Parcelable