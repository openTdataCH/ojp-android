package ch.opentransportdata.ojp.data.dto.response.tr.situations

import android.os.Parcelable
import ch.opentransportdata.ojp.data.dto.SIRI_NAME_SPACE
import ch.opentransportdata.ojp.data.dto.SIRI_PREFIX
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

/**
 * Created by Nico Brandenberger on 18.09.2026
 */
@Parcelize
@Serializable
@XmlSerialName("AffectedStopPlace", SIRI_NAME_SPACE, SIRI_PREFIX)
data class AffectedStopPlaceDto(
    @XmlElement(true)
    @XmlSerialName("StopPlaceRef", SIRI_NAME_SPACE, SIRI_PREFIX)
    val stopPlaceRef: String? = null,

    @XmlElement(true)
    @XmlSerialName("PlaceName", SIRI_NAME_SPACE, SIRI_PREFIX)
    val placeName: String? = null
) : Parcelable
