package ch.opentransportdata.ojp.data.dto.response.tiri

import android.os.Parcelable
import ch.opentransportdata.ojp.data.dto.OJP_NAME_SPACE
import ch.opentransportdata.ojp.data.dto.response.tir.leg.DatedJourneyDto
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

/**
 * Created by Deniz Kalem on 26.03.2026
 */
@Parcelize
@Serializable
@XmlSerialName("TripInfoResult", OJP_NAME_SPACE, "")
data class TripInfoResultDto(
    @XmlElement(true)
    @XmlSerialName("PreviousCall", OJP_NAME_SPACE, "")
    val previousCalls: List<CallAtStopDto>? = null,

    @XmlElement(true)
    @XmlSerialName("OnwardCall", OJP_NAME_SPACE, "")
    val onwardCalls: List<CallAtStopDto>? = null,

    @XmlElement(true)
    @XmlSerialName("Service", OJP_NAME_SPACE, "")
    val service: DatedJourneyDto? = null,

    @XmlElement(true)
    @XmlSerialName("JourneyTrack", OJP_NAME_SPACE, "")
    val journeyTrack: JourneyTrackDto? = null,
) : Parcelable