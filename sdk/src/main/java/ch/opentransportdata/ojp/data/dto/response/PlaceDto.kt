package ch.opentransportdata.ojp.data.dto.response

import android.os.Parcelable
import ch.opentransportdata.ojp.data.dto.OJP_NAME_SPACE
import ch.opentransportdata.ojp.data.dto.response.place.AbstractPlaceDto
import ch.opentransportdata.ojp.data.dto.response.place.AddressDto
import ch.opentransportdata.ojp.data.dto.response.place.PointOfInterestDto
import ch.opentransportdata.ojp.data.dto.response.place.StopPlaceDto
import ch.opentransportdata.ojp.data.dto.response.place.StopPointDto
import ch.opentransportdata.ojp.data.dto.response.place.TopographicPlaceDto
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
@XmlSerialName("Place", OJP_NAME_SPACE, "")
data class PlaceDto(
    @XmlElement(true)
    @XmlSerialName("StopPoint", OJP_NAME_SPACE, "")
    val stopPoint: StopPointDto? = null,

    @XmlElement(true)
    @XmlSerialName("StopPlace", OJP_NAME_SPACE, "")
    val stopPlace: StopPlaceDto? = null,

    @XmlElement(true)
    @XmlSerialName("TopographicPlace", OJP_NAME_SPACE, "")
    val topographicPlace: TopographicPlaceDto? = null,

    @XmlElement(true)
    @XmlSerialName("PointOfInterest", OJP_NAME_SPACE, "")
    val pointOfInterest: PointOfInterestDto? = null,

    @XmlElement(true)
    @XmlSerialName("Address", OJP_NAME_SPACE, "")
    val address: AddressDto? = null,

    @XmlElement(true)
    @XmlSerialName("Name", OJP_NAME_SPACE, "")
    val name: NameDto? = null,

    @XmlElement(true)
    @XmlSerialName("GeoPosition", OJP_NAME_SPACE, "")
    val position: GeoPositionDto? = null,

    @XmlElement(true)
    @XmlSerialName("Mode", OJP_NAME_SPACE, "")
    val mode: List<ModeDto>? = emptyList(),
) : Parcelable {

    val placeType: AbstractPlaceDto?
        get() = listOfNotNull(stopPoint, stopPlace, topographicPlace, pointOfInterest, address).singleOrNull()
}