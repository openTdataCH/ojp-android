package ch.opentransportdata.ojp.data.dto.request.ser

import ch.opentransportdata.ojp.data.dto.OJP_NAME_SPACE
import ch.opentransportdata.ojp.data.dto.SIRI_NAME_SPACE
import ch.opentransportdata.ojp.data.dto.SIRI_PREFIX
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

/**
 * Created by Deniz Kalem on 20.05.2026
 */
@Serializable
@XmlSerialName("OperatorFilter", OJP_NAME_SPACE, "")
internal data class OperatorFilterDto(

    @XmlElement(true)
    @XmlSerialName("OperatorRef", SIRI_NAME_SPACE, SIRI_PREFIX)
    val operatorRef: List<String>? = null,

    @XmlElement(true)
    @XmlSerialName("Exclude", OJP_NAME_SPACE, "")
    val exclude: Boolean? = null,
)