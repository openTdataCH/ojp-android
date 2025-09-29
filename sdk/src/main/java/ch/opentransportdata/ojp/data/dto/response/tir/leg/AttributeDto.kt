package ch.opentransportdata.ojp.data.dto.response.tir.leg

import android.os.Parcelable
import ch.opentransportdata.ojp.data.dto.OJP_NAME_SPACE
import ch.opentransportdata.ojp.data.dto.response.NameDto
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

/**
 * Created by Michael Ruppen on 28.06.2024
 */
@Parcelize
@Serializable
@XmlSerialName("Attribute", OJP_NAME_SPACE, "")
data class AttributeDto(
    @XmlElement(true)
    @XmlSerialName("UserText", OJP_NAME_SPACE, "")
    val userText: NameDto,

    @XmlElement(true)
    @XmlSerialName("Code", OJP_NAME_SPACE, "")
    val code: String
) : Parcelable