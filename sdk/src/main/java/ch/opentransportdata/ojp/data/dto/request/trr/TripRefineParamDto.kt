package ch.opentransportdata.ojp.data.dto.request.trr

import ch.opentransportdata.ojp.domain.model.RealtimeData
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

/**
 * Created by Nico Brandenberger on 07.04.2025
 */

@Xml(name = "Params")
internal data class TripRefineParamDto(
    @PropertyElement(name = "IncludeTrackSections")
    val includeTrackSections: Boolean? = null,

    @PropertyElement(name = "IncludeLegProjection")
    val includeLegProjection: Boolean? = null,

    @PropertyElement(name = "IncludeTurnDescription")
    val includeTurnDescription: Boolean? = null,

    @PropertyElement(name = "IncludeIntermediateStops")
    val includeIntermediateStops: Boolean? = null,

    @PropertyElement(name = "IncludeAllRestrictedLines")
    val includeAllRestrictedLines: Boolean? = null,

    @PropertyElement(name = "UseRealtimeData")
    val useRealtimeData: RealtimeData? = null,
)
