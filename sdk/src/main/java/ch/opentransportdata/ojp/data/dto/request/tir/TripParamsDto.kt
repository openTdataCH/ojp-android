package ch.opentransportdata.ojp.data.dto.request.tir

import ch.opentransportdata.ojp.domain.model.PtMode
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

/**
 * Created by Michael Ruppen on 07.06.2024
 */
@Xml(name = "Params")
data class TripParamsDto(
    @PropertyElement(name = "NumberOfResults")
    val numberOfResults: Int? = null,

    @PropertyElement(name = "NumberOfResultsBefore")
    val numberOfResultsBefore: Int? = null,

    @PropertyElement(name = "NumberOfResultsAfter")
    val numberOfResultsAfter: Int? = null,

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

    @Element(name = "ModeAndModeOfOperationFilter")
    val modeAndModeOfOperationFilter: List<ModeAndModeOfOperationFilter>?
)
