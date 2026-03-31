package ch.opentransportdata.ojp.data.dto.response.tiri

import android.os.Parcelable
import ch.opentransportdata.ojp.data.dto.OJP_NAME_SPACE
import ch.opentransportdata.ojp.data.dto.response.tir.leg.TrackSectionDto
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

/**
 * Created by Deniz Kalem on 26.03.2026
 */
@Parcelize
@Serializable
@XmlSerialName("JourneyTrack", OJP_NAME_SPACE, "")
data class JourneyTrackDto(
    @XmlElement(true)
    @XmlSerialName("TrackSection", OJP_NAME_SPACE, "")
    val trackSections: List<TrackSectionDto>
) : Parcelable