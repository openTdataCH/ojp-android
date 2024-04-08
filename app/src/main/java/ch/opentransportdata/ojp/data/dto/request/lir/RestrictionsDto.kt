package ch.opentransportdata.ojp.data.dto.request.lir

import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml


/**
 * Created by Michael Ruppen on 08.04.2024
 */
@Xml(name = "Restrictions")
data class RestrictionsDto(
    @PropertyElement(name = "Type")
    val type: String,

    @PropertyElement(name = "NumberOfResults")
    val numberOfResults: Int,

    @PropertyElement(name = "IncludePtModes")
    val ptModeIncluded: Boolean
)
