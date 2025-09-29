package ch.opentransportdata.ojp.data.dto.response.tir

import android.os.Parcelable
import ch.opentransportdata.ojp.data.dto.OJP_NAME_SPACE
import ch.opentransportdata.ojp.data.dto.response.tir.trips.TripDto
import ch.opentransportdata.ojp.data.dto.response.tir.trips.minimalCopy
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

/**
 * Created by Michael Ruppen on 28.06.2024
 */
@Parcelize
@Serializable
@XmlSerialName("TripResult", OJP_NAME_SPACE, "")
data class TripResultDto(
    @XmlElement(true)
    @XmlSerialName("Id", OJP_NAME_SPACE, "")
    val id: String,

    @XmlElement(true)
    @XmlSerialName("Trip", OJP_NAME_SPACE, "")
    val trip: TripDto? = null,

    @XmlElement(true)
    @XmlSerialName("IsAlternativeOption", OJP_NAME_SPACE, "")
    val isAlternativeOption: Boolean? = null
) : Parcelable

val TripResultDto.minimalTripResult: TripResultDto
    get() = TripResultDto(
        trip = trip?.minimalCopy(places = null),
        id = id,
        isAlternativeOption = null
    )