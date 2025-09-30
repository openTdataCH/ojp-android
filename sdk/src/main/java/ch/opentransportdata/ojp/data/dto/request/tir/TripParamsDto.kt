package ch.opentransportdata.ojp.data.dto.request.tir

import ch.opentransportdata.ojp.data.dto.OJP_NAME_SPACE
import ch.opentransportdata.ojp.domain.model.RealtimeData
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

/**
 * Created by Michael Ruppen on 07.06.2024
 */
@Serializable
@XmlSerialName("Params", OJP_NAME_SPACE, "")
internal data class TripParamsDto(

    @XmlElement(true)
    @XmlSerialName("NumberOfResults", OJP_NAME_SPACE, "")
    val numberOfResults: Int? = null,

    @XmlElement(true)
    @XmlSerialName("NumberOfResultsBefore", OJP_NAME_SPACE, "")
    val numberOfResultsBefore: Int? = null,

    @XmlElement(true)
    @XmlSerialName("NumberOfResultsAfter", OJP_NAME_SPACE, "")
    val numberOfResultsAfter: Int? = null,

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

    @XmlElement(true)
    @XmlSerialName("ModeAndModeOfOperationFilter", OJP_NAME_SPACE, "")
    val modeAndModeOfOperationFilter: List<ModeAndModeOfOperationFilterDto>? = null
)