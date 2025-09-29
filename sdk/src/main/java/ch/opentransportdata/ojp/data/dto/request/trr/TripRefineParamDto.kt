package ch.opentransportdata.ojp.data.dto.request.trr

import ch.opentransportdata.ojp.data.dto.OJP_NAME_SPACE
import ch.opentransportdata.ojp.domain.model.RealtimeData
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

/**
 * Created by Nico Brandenberger on 07.04.2025
 */

@Serializable
@XmlSerialName("Params", OJP_NAME_SPACE, "")
internal data class TripRefineParamDto(

    @XmlElement(true)
    @XmlSerialName("IncludeTrackSections", OJP_NAME_SPACE, "")
    val includeTrackSections: Boolean? = null,

    @XmlElement(true)
    @XmlSerialName("IncludeLegProjection", OJP_NAME_SPACE, "")
    val includeLegProjection: Boolean? = null,

    @XmlElement(true)
    @XmlSerialName("IncludeTurnDescription", OJP_NAME_SPACE, "")
    val includeTurnDescription: Boolean? = null,

    @XmlElement(true)
    @XmlSerialName("IncludeIntermediateStops", OJP_NAME_SPACE, "")
    val includeIntermediateStops: Boolean? = null,

    @XmlElement(true)
    @XmlSerialName("IncludeAllRestrictedLines", OJP_NAME_SPACE, "")
    val includeAllRestrictedLines: Boolean? = null,

    @XmlElement(true)
    @XmlSerialName("UseRealtimeData", OJP_NAME_SPACE, "")
    val useRealtimeData: RealtimeData? = null,
)