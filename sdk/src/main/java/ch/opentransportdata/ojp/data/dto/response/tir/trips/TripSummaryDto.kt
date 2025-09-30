package ch.opentransportdata.ojp.data.dto.response.tir.trips

import ch.opentransportdata.ojp.data.dto.OJP_NAME_SPACE
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

/**
 * Created by Michael Ruppen on 01.07.2024
 */
@Parcelize
@Serializable
@XmlSerialName("TripSummary", OJP_NAME_SPACE, "")
internal data class TripSummaryDto(
    @XmlElement(true)
    @XmlSerialName("TripId", OJP_NAME_SPACE, "")
    override val id: String
) : AbstractTripDto()