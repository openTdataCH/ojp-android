package ch.opentransportdata.ojp.data.dto.request.tir

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
    val numberOfResultsBefore: Int? = null, //todo: check if makes difference setting them to 0 as default or if they have to be nullable

    @PropertyElement(name = "NumberOfResultsAfter")
    val numberOfResultsAfter: Int? = null,//todo: check if makes difference setting them to 0 as default or if they have to be nullable

    @PropertyElement(name = "IncludeTrackSections")
    val includeTrackSections: Boolean? = null,//todo: check if makes difference setting them to false as default or if they have to be nullable

    @PropertyElement(name = "IncludeLegProjection")
    val includeLegProjection: Boolean? = null,//todo: check if makes difference setting them to false as default or if they have to be nullable

    @PropertyElement(name = "IncludeTurnDescription")
    val includeTurnDescription: Boolean? = null,//todo: check if makes difference setting them to false as default or if they have to be nullable

    @PropertyElement(name = "IncludeIntermediateStops")
    val includeIntermediateStops: Boolean? = null,//todo: check if makes difference setting them to false as default or if they have to be nullable
)
