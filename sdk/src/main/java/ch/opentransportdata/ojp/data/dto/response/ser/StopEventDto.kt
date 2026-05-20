package ch.opentransportdata.ojp.data.dto.response.ser

import android.os.Parcelable
import ch.opentransportdata.ojp.data.dto.OJP_NAME_SPACE
import ch.opentransportdata.ojp.data.dto.response.NameDto
import ch.opentransportdata.ojp.data.dto.response.tr.leg.DatedJourneyDto
import ch.opentransportdata.ojp.data.dto.response.tr.leg.SituationFullRefs
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

/**
 * Created by Deniz Kalem on 20.05.2026
 */
@Parcelize
@Serializable
@XmlSerialName("StopEvent", OJP_NAME_SPACE, "")
data class StopEventDto(
    @XmlElement(true)
    @XmlSerialName("PreviousCall", OJP_NAME_SPACE, "")
    val previousCalls: List<CallAtNearStopDto>? = null,

    @XmlElement(true)
    @XmlSerialName("ThisCall", OJP_NAME_SPACE, "")
    val thisCall: CallAtNearStopDto,

    @XmlElement(true)
    @XmlSerialName("OnwardCall", OJP_NAME_SPACE, "")
    val onwardCalls: List<CallAtNearStopDto>? = null,

    @XmlElement(true)
    @XmlSerialName("Service", OJP_NAME_SPACE, "")
    val service: DatedJourneyDto,

    @XmlElement(true)
    val operatingDays: OperatingDaysDto? = null,

    @XmlElement(true)
    @XmlSerialName("OperatingDaysDescription", OJP_NAME_SPACE, "")
    val operatingDaysDescription: NameDto? = null,

    @XmlElement(true)
    @XmlSerialName("SituationFullRefs", OJP_NAME_SPACE, "")
    val situationFullRefs: SituationFullRefs? = null,
) : Parcelable