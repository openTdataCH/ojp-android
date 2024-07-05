package ch.opentransportdata.ojp.data.dto.response.delivery

import java.time.LocalDateTime

/**
 * Created by Michael Ruppen on 14.05.2024
 */
abstract class AbstractDeliveryDto {
    abstract val responseTimestamp: LocalDateTime
    abstract val requestMessageRef: String?
    abstract val defaultLanguage: String?
}