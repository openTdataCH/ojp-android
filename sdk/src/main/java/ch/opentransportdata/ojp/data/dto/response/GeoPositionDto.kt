package ch.opentransportdata.ojp.data.dto.response

import android.os.Parcelable
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

/**
 * Created by Michael Ruppen on 08.04.2024
 */
@Serializable
@Parcelize
@Xml(name = "GeoPosition")
data class GeoPositionDto(
    @PropertyElement(name = "siri:Longitude")
    val longitude: Double,
    @PropertyElement(name = "siri:Latitude")
    val latitude: Double
): Parcelable