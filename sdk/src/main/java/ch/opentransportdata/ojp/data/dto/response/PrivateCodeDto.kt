package ch.opentransportdata.ojp.data.dto.response

import android.os.Parcelable
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

/**
 * Created by Michael Ruppen on 26.04.2024
 *
 * Serializable and Parcelize annotation is both needed for compose navigation with custom types
 */
@Serializable
@Parcelize
@Xml(name = "PrivateCode")
data class PrivateCodeDto(
    @PropertyElement(name = "System")
    val system: String,
    @PropertyElement(name = "Value")
    val value: String,
) : Parcelable
