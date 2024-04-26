package ch.opentransportdata.ojp.data.dto.request.lir

import ch.opentransportdata.ojp.domain.model.PlaceTypeRestriction
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml


/**
 * Created by Michael Ruppen on 08.04.2024
 */
@Xml(name = "Restrictions")
internal data class RestrictionsDto(
    @PropertyElement(name = "Type")
    val types: PlaceTypeRestriction,

    @PropertyElement(name = "NumberOfResults")
    val numberOfResults: Int,

    @PropertyElement(name = "IncludePtModes")
    val ptModeIncluded: Boolean
)
