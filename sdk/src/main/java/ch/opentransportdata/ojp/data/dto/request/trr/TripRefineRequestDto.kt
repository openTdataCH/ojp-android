package ch.opentransportdata.ojp.data.dto.request.trr

import ch.opentransportdata.ojp.data.dto.response.tir.TripResultDto
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml
import java.time.LocalDateTime

/**
 * Created by Nico Brandenberger on 07.04.2025
 */

@Xml(name = "OJPTripRefineRequest")
internal data class TripRefineRequestDto(
    @PropertyElement(name = "siri:RequestTimestamp")
    val requestTimestamp: LocalDateTime,

    @Element(name = "RefineParams")
    val params: TripRefineParamDto?,

    @Element(name = "TripResult")
    val result: TripResultDto,
)

