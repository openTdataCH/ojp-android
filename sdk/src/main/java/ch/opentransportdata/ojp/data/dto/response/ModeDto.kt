package ch.opentransportdata.ojp.data.dto.response

import android.os.Parcelable
import ch.opentransportdata.ojp.domain.model.PtMode
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

/**
 * Created by Michael Ruppen on 08.04.2024
 */
@Serializable
@Parcelize
@Xml(name = "Mode")
data class ModeDto(
    @PropertyElement(name = "PtMode")
    val ptMode: PtMode
):Parcelable