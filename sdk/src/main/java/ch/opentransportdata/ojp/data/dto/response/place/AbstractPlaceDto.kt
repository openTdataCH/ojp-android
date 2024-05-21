package ch.opentransportdata.ojp.data.dto.response.place

import ch.opentransportdata.ojp.data.dto.response.PrivateCodeDto

/**
 * Created by Michael Ruppen on 15.05.2024
 */
abstract class AbstractPlaceDto {
    abstract val privateCodes: List<PrivateCodeDto>?
}