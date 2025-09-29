package ch.opentransportdata.ojp.data.dto.request.lir

import ch.opentransportdata.ojp.data.dto.OJP_NAME_SPACE
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

/**
 * Created by Michael Ruppen on 08.04.2024
 */

@Serializable
@XmlSerialName("InitialInput", OJP_NAME_SPACE, "")
internal data class InitialInputDto(

    @XmlElement(true)
    @XmlSerialName("GeoRestriction", OJP_NAME_SPACE, "")
    val geoRestriction: GeoRestrictionDto? = null,

    @XmlElement(true)
    @XmlSerialName("Name", OJP_NAME_SPACE, "")
    val name: String? = null
)