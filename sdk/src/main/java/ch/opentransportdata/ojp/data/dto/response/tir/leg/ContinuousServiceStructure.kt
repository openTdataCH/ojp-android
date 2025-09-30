package ch.opentransportdata.ojp.data.dto.response.tir.leg

import android.os.Parcelable
import ch.opentransportdata.ojp.data.dto.OJP_NAME_SPACE
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

/**
 * Created by Nico Brandenberger on 02.07.2025
 */

@Parcelize
@Serializable
@XmlSerialName("ContinuousServiceTypeChoice", OJP_NAME_SPACE, "")
data class ContinuousServiceTypeChoiceDto(
    @XmlElement(true)
    @XmlSerialName("PersonalService", OJP_NAME_SPACE, "")
    val personalService: PersonalServiceDto? = null,

    @XmlElement(true)
    @XmlSerialName("DatedJourney", OJP_NAME_SPACE, "")
    val datedJourneyDto: DatedJourneyDto? = null
) : Parcelable

@Parcelize
@Serializable
@XmlSerialName("PersonalService", OJP_NAME_SPACE, "")
data class PersonalServiceDto(
    @XmlElement(true)
    @XmlSerialName("PersonalMode", OJP_NAME_SPACE, "")
    val personalMode: String
) : Parcelable