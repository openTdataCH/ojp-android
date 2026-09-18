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
@XmlSerialName("StopPlaces", SIRI_NAME_SPACE, SIRI_PREFIX)
data class StopPlacesDto(
    @XmlElement(true)
    @XmlSerialName("AffectedStopPlace", SIRI_NAME_SPACE, SIRI_PREFIX)
    val affectedStopPlaces: List<AffectedStopPlaceDto>? = emptyList()
) : Parcelable
