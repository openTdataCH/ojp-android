package ch.opentransportdata.ojp.data.dto.response

import ch.opentransportdata.ojp.data.dto.response.delivery.AbstractDeliveryDto
import java.time.LocalDateTime

/**
 * Created by Michael Ruppen on 08.04.2024
 */
internal data class ServiceDeliveryDto(
    val responseTimestamp: LocalDateTime,
    val producerRef: String?,
    val ojpDelivery: AbstractDeliveryDto,
)
