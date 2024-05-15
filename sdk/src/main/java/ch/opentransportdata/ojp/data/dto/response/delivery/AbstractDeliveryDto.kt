package ch.opentransportdata.ojp.data.dto.response.delivery

/**
 * Created by Michael Ruppen on 14.05.2024
 */
abstract class AbstractDeliveryDto {
    abstract val responseTimestamp: String
    abstract val requestMessageRef: String?
    abstract val defaultLanguage: String?
}