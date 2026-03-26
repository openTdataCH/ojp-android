package ch.opentransportdata.ojp.data.dto.request.tr

import ch.opentransportdata.ojp.data.dto.OJP_NAME_SPACE
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

/**
 * Created by Nico Brandenberger on 20.02.2026
 */
@Serializable
@XmlSerialName("ItModeAndModeOfOperation", OJP_NAME_SPACE, "")
data class ItModeAndModeOfOperationDto(
    @XmlElement(true)
    @XmlSerialName("PersonalMode", OJP_NAME_SPACE, "")
    val personalMode: String? = null,

    @XmlElement(true)
    @XmlSerialName("PersonalModeOfOperation", OJP_NAME_SPACE, "")
    val personalModeOfOperation: String? = null,
)
