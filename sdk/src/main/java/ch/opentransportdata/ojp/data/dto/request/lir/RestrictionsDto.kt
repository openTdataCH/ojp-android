package ch.opentransportdata.ojp.data.dto.request.lir

import ch.opentransportdata.ojp.data.dto.OJP_NAME_SPACE
import ch.opentransportdata.ojp.domain.model.PlaceTypeRestriction
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName
import nl.adaptivity.xmlutil.serialization.XmlValue


/**
 * Created by Michael Ruppen on 08.04.2024
 */
@Serializable
@XmlSerialName("Restrictions", OJP_NAME_SPACE, "")
internal data class RestrictionsDto(
    @XmlElement(true)
    @XmlSerialName("Type", OJP_NAME_SPACE, "")
    val types: List<PlaceTypeRestriction>,

    @XmlElement(true)
    @XmlSerialName("NumberOfResults", OJP_NAME_SPACE, "")
    val numberOfResults: Int,

    @XmlElement(true)
    @XmlSerialName("IncludePtModes", OJP_NAME_SPACE, "")
    val ptModeIncluded: Boolean
)