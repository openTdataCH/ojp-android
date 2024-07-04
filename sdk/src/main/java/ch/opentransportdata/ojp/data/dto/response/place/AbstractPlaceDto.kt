package ch.opentransportdata.ojp.data.dto.response.place

import android.os.Parcelable
import ch.opentransportdata.ojp.data.dto.response.PrivateCodeDto
import kotlinx.serialization.Serializable

/**
 * Created by Michael Ruppen on 15.05.2024
 */
@Serializable
abstract class AbstractPlaceDto: Parcelable {
    abstract val privateCodes: List<PrivateCodeDto>?
}