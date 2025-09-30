package ch.opentransportdata.ojp.data.dto.response.tir.leg

import android.os.Parcelable
import ch.opentransportdata.ojp.data.dto.OJP_NAME_SPACE
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

/**
 * Created by Michael Ruppen on 28.06.2024
 */
@Parcelize
@Serializable
@XmlSerialName("TrackSection", OJP_NAME_SPACE, "")
data class TrackSectionDto(
    //todo: check if needed or can be summarized
//    val trackSectionStart: TrackSectionStopPlaceRef?,
//    val trackSectionEnd: TrackSectionStopPlaceRef?,
    @XmlElement(true)
    @XmlSerialName("LinkProjection", OJP_NAME_SPACE, "")
    val linkProjection: LinearShapeDto? = null
) : Parcelable