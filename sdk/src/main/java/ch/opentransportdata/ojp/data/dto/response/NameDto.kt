package ch.opentransportdata.ojp.data.dto.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

/**
 * Created by Michael Ruppen on 08.04.2024
 *
 * Serializable and Parcelize annotation is both needed for compose navigation with custom types
 */
@Parcelize
@Serializable
@XmlSerialName("Name")
data class NameDto(
    @XmlElement(true)
    @XmlSerialName("Text")
    val text: String? = null
) : Parcelable