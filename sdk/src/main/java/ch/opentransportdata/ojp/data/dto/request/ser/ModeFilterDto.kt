package ch.opentransportdata.ojp.data.dto.request.ser

import ch.opentransportdata.ojp.data.dto.OJP_NAME_SPACE
import ch.opentransportdata.ojp.domain.model.PtMode
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

/**
 * Created by Deniz Kalem on 20.05.2026
 */
@Serializable
@XmlSerialName("ModeFilter", OJP_NAME_SPACE, "")
internal data class ModeFilterDto(

    @XmlElement(true)
    @XmlSerialName("PtMode", OJP_NAME_SPACE, "")
    val ptMode: List<PtMode>? = null,

    @XmlElement(true)
    @XmlSerialName("Exclude", OJP_NAME_SPACE, "")
    val exclude: Boolean? = null,
)