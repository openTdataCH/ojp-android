package ch.opentransportdata.ojp.data.dto.response.tir

import ch.opentransportdata.ojp.data.dto.response.tir.situations.SituationDto
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Xml

/**
 * Created by Michael Ruppen on 28.06.2024
 */
@Xml(name = "TripResponseContext")
data class TripResponseContextDto(
    @Element(name = "Situations")
    val situation: SituationDto
)
