package ch.opentransportdata.ojp.data.dto.response.tir.situations

import android.os.Parcelable
import ch.opentransportdata.ojp.data.dto.SIRI_NAME_SPACE
import ch.opentransportdata.ojp.data.dto.SIRI_PREFIX
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

/**
 * Created by Michael Ruppen on 28.06.2024
 */
@Parcelize
@Serializable
@XmlSerialName("Blocking", SIRI_NAME_SPACE, SIRI_PREFIX)
data class BlockingDto(
    @XmlElement(true)
    @XmlSerialName("JourneyPlanner", SIRI_NAME_SPACE, SIRI_PREFIX)
    val journeyPlanner: Boolean
) : Parcelable
