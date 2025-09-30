package ch.opentransportdata.ojp.data.dto.request.tir

import ch.opentransportdata.ojp.data.dto.OJP_NAME_SPACE
import ch.opentransportdata.ojp.domain.model.PtMode
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

/**
 * Created by Michael Ruppen on 31.07.2024
 */
@Serializable
@XmlSerialName("ModeAndModeOfOperationFilter", OJP_NAME_SPACE, "")
internal data class ModeAndModeOfOperationFilterDto(
    @XmlElement(true)
    @XmlSerialName("PtMode", OJP_NAME_SPACE, "")
    val ptMode: List<PtMode>?,

    @XmlElement(true)
    @XmlSerialName("Exclude", OJP_NAME_SPACE, "")
    val exclude: Boolean? = null
)
