package ch.opentransportdata.ojp.data.dto.response

import ch.opentransportdata.ojp.data.dto.response.delivery.AbstractDeliveryDto

/**
 * Created by Michael Ruppen on 08.04.2024
 */
internal data class ServiceDeliveryDto(
    val responseTimestamp: String,
    val producerRef: String?,
    val ojpDelivery: AbstractDeliveryDto,
)
