package ch.opentransportdata.ojp.data.dto.response

import ch.opentransportdata.ojp.data.dto.response.delivery.AbstractDeliveryDto
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml
import java.time.LocalDateTime

/**
 * Created by Michael Ruppen on 08.04.2024
 */
@Xml(name = "siri:ServiceDelivery")
internal data class ServiceDeliveryDto(
    @PropertyElement(name = "siri:ResponseTimestamp")
    val responseTimestamp: LocalDateTime,
    @PropertyElement(name = "siri:ProducerRef")
    val producerRef: String?,
    @Element
    val ojpDelivery: AbstractDeliveryDto,
)
