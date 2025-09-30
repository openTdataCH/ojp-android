package ch.opentransportdata.ojp.data.dto.request.tir

import android.os.Parcelable
import ch.opentransportdata.ojp.data.dto.OJP_NAME_SPACE
import ch.opentransportdata.ojp.data.dto.response.GeoPositionDto
import ch.opentransportdata.ojp.data.dto.response.NameDto
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

/**
 * Created by Michael Ruppen on 07.06.2024
 *
 * Either [ref] and [stationName] has to be set or [position].
 */
//todo: normally should create all the reference objects
@Parcelize
@Serializable
@XmlSerialName("PlaceRef", OJP_NAME_SPACE, "")
data class PlaceReferenceDto(
    @XmlElement(true)
    @XmlSerialName("StopPlaceRef", OJP_NAME_SPACE, "")
    val ref: String? = null,
    @XmlElement(true)
    @XmlSerialName("Name", OJP_NAME_SPACE, "")
    val stationName: NameDto? = null,
    @XmlElement(true)
    @XmlSerialName("GeoPosition", OJP_NAME_SPACE, "")
    val position: GeoPositionDto? = null
) : Parcelable