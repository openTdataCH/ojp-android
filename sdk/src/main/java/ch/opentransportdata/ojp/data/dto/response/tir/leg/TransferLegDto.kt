package ch.opentransportdata.ojp.data.dto.response.tir.leg

import ch.opentransportdata.ojp.domain.model.TransferType
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml
import kotlinx.parcelize.Parcelize
import java.time.Duration

/**
 * Created by Michael Ruppen on 28.06.2024
 */
@Parcelize
@Xml(name = "TransferLeg")
data class TransferLegDto(
    //normally a list, but is only a single return type implemented on backend. tikXml can't parse a list of ENUM! if want to make it run, try custom type adapter with a List
    @PropertyElement(name = "TransferType")
    val transferType: TransferType,
    @Element(name = "LegStart")
    val legStart: LegStartEndDto,
    @Element(name = "LegEnd")
    val legEnd: LegStartEndDto,
    @PropertyElement(name = "Duration")
    val duration: Duration
) : AbstractLegType()