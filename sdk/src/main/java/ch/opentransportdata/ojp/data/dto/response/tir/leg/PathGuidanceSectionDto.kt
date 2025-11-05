package ch.opentransportdata.ojp.data.dto.response.tir.leg

import android.os.Parcelable
import ch.opentransportdata.ojp.data.dto.OJP_NAME_SPACE
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

/**
 * Created by Nico Brandenberger on 04.11.2025
 */

@Parcelize
@Serializable
data class PathGuidanceSectionDto(
    @XmlElement(true)
    @XmlSerialName("TrackSection", OJP_NAME_SPACE, "")
    val trackSection: List<TrackSectionDto>
) : Parcelable