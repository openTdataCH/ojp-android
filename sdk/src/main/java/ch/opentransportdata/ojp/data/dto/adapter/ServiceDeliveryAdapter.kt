package ch.opentransportdata.ojp.data.dto.adapter

import ch.opentransportdata.ojp.data.dto.response.delivery.LocationInformationDeliveryDto
import ch.opentransportdata.ojp.data.dto.response.ServiceDeliveryDto
import ch.opentransportdata.ojp.data.dto.response.delivery.AbstractDeliveryDto
import ch.opentransportdata.ojp.data.dto.response.delivery.TripDeliveryDto
import com.tickaroo.tikxml.TikXmlConfig
import com.tickaroo.tikxml.XmlReader
import com.tickaroo.tikxml.XmlWriter
import com.tickaroo.tikxml.typeadapter.TypeAdapter


/**
 * Created by Michael Ruppen on 14.05.2024
 *
 * Adapter to map all possible delivery objects correctly
 */

//todo: implement tests
internal class ServiceDeliveryAdapter : TypeAdapter<ServiceDeliveryDto> {

    override fun fromXml(reader: XmlReader?, config: TikXmlConfig?, isGenericList: Boolean): ServiceDeliveryDto {
        var responseTime: String? = null
        var producerRef: String? = null
        var deliveryResponse: AbstractDeliveryDto? = null

        while (reader?.hasElement() == true) {
            reader.beginElement()
            val nextElement = reader.nextElementName()

            if (nextElement == "siri:ResponseTimestamp" && reader.hasTextContent()) {
                responseTime = reader.nextTextContent()
            }
            if (nextElement == "siri:ProducerRef" && reader.hasTextContent()) {
                producerRef = reader.nextTextContent()
            }
            if (nextElement == "OJPLocationInformationDelivery" && reader.hasElement()) {
                deliveryResponse =
                    config?.getTypeAdapter<LocationInformationDeliveryDto>(LocationInformationDeliveryDto::class.java)
                        ?.fromXml(reader, config, isGenericList)
            }

            if (nextElement == "OJPTripDelivery" && reader.hasElement()) {
                deliveryResponse =
                    config?.getTypeAdapter<TripDeliveryDto>(TripDeliveryDto::class.java)
                        ?.fromXml(reader, config, isGenericList)
            }

            reader.endElement()
        }

        if (responseTime == null) throw NullPointerException("Missing element ResponseTimestamp")
        if (deliveryResponse == null) throw NullPointerException("Missing element delivery object")

        return ServiceDeliveryDto(
            responseTimestamp = responseTime,
            producerRef = producerRef,
            ojpDelivery = deliveryResponse
        )
    }

    override fun toXml(
        writer: XmlWriter?,
        config: TikXmlConfig?,
        value: ServiceDeliveryDto?,
        overridingXmlElementTagName: String?
    ) {
        value?.let {
            when (overridingXmlElementTagName == null) {
                true -> writer?.beginElement("ServiceDelivery")
                else -> writer?.beginElement(overridingXmlElementTagName)
            }
            writer?.beginElement("siri:ResponseTimestamp")
            writer?.textContent(it.responseTimestamp)
            writer?.endElement()

            if (it.producerRef != null) {
                writer?.beginElement("siri:ProducerRef")
                writer?.textContent(it.producerRef)
                writer?.endElement()
            }

            when (it.ojpDelivery) {
                is LocationInformationDeliveryDto -> {
                    config?.getTypeAdapter<LocationInformationDeliveryDto>(LocationInformationDeliveryDto::class.java)
                        ?.toXml(writer, config, it.ojpDelivery, "OJPLocationInformationDelivery")
                }

                is TripDeliveryDto -> {
                    config?.getTypeAdapter<TripDeliveryDto>(TripDeliveryDto::class.java)
                        ?.toXml(writer, config, it.ojpDelivery, "OJPTripDelivery")
                }
            }

            writer?.endElement()
        }

    }
}