package ch.opentransportdata.ojp.data.dto.response.tir.leg

import android.os.Parcelable
import ch.opentransportdata.ojp.data.dto.SIRI_NAME_SPACE
import ch.opentransportdata.ojp.data.dto.SIRI_PREFIX
import ch.opentransportdata.ojp.data.dto.converter.FareClassSerializer
import ch.opentransportdata.ojp.domain.model.FareClass
import ch.opentransportdata.ojp.domain.model.OccupancyLevel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

/**
 * Created by Deniz Kalem on 25.06.2025
 */
@Parcelize
@Serializable
@XmlSerialName("ExpectedDepartureOccupancy", SIRI_NAME_SPACE, SIRI_PREFIX)
data class ExpectedDepartureOccupancyDto(
    @XmlElement(true)
    @XmlSerialName("FareClass", SIRI_NAME_SPACE, SIRI_PREFIX)
    @Serializable(with = FareClassSerializer::class)
    val fareClass: FareClass,

    @XmlElement(true)
    @XmlSerialName("OccupancyLevel", SIRI_NAME_SPACE, SIRI_PREFIX)
    val occupancyLevel: OccupancyLevel
) : Parcelable