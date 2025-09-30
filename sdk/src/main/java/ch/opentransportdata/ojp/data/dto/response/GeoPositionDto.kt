package ch.opentransportdata.ojp.data.dto.response

import android.os.Parcelable
import ch.opentransportdata.ojp.data.dto.OJP_NAME_SPACE
import ch.opentransportdata.ojp.data.dto.SIRI_NAME_SPACE
import ch.opentransportdata.ojp.data.dto.SIRI_PREFIX
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

/**
 * Created by Michael Ruppen on 08.04.2024
 *
 * Serializable and Parcelize annotation is both needed for compose navigation with custom types
 */
@Parcelize
@Serializable
@XmlSerialName("GeoPosition", OJP_NAME_SPACE, "")
data class GeoPositionDto(
    @XmlElement(true)
    @XmlSerialName("Longitude", SIRI_NAME_SPACE, SIRI_PREFIX)
    val longitude: Double,

    @XmlElement(true)
    @XmlSerialName("Latitude", SIRI_NAME_SPACE, SIRI_PREFIX)
    val latitude: Double
) : Parcelable