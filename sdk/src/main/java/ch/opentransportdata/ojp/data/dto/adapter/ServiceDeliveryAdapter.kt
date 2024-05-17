package ch.opentransportdata.ojp.data.dto.adapter

import ch.opentransportdata.ojp.data.dto.response.ServiceDeliveryDto
import ch.opentransportdata.ojp.data.dto.response.delivery.AbstractDeliveryDto
import ch.opentransportdata.ojp.data.dto.response.delivery.LocationInformationDeliveryDto
import ch.opentransportdata.ojp.data.dto.response.delivery.TripDeliveryDto
import com.tickaroo.tikxml.TikXmlConfig
import com.tickaroo.tikxml.XmlReader
import com.tickaroo.tikxml.XmlWriter
import com.tickaroo.tikxml.typeadapter.ChildElementBinder
import com.tickaroo.tikxml.typeadapter.TypeAdapter
import java.io.IOException


/**
 * Created by Michael Ruppen on 14.05.2024
 *
 * Adapter to map all possible delivery objects correctly
 */

//todo: implement tests
internal class ServiceDeliveryAdapter : TypeAdapter<ServiceDeliveryDto> {

    private val childElementBinders: HashMap<String, ChildElementBinder<ValueHolder>> = hashMapOf()

    init {
        childElementBinders["siri:ResponseTimestamp"] = ChildElementBinder<ValueHolder> { reader, config, value ->
            while (reader.hasAttribute()) {
                val attributeName = reader.nextAttributeName()
                if (config.exceptionOnUnreadXml() && !attributeName.startsWith("xmlns")) {
                    throw IOException("Unread attribute '" + attributeName + "' at path " + reader.path)
                }
                reader.skipAttributeValue()
            }
            value.responseTimestamp = reader.nextTextContent()
        }

        childElementBinders["siri:ProducerRef"] = ChildElementBinder<ValueHolder> { reader, config, value ->
            while (reader.hasAttribute()) {
                val attributeName = reader.nextAttributeName()
                if (config.exceptionOnUnreadXml() && !attributeName.startsWith("xmlns")) {
                    throw IOException("Unread attribute '" + attributeName + "' at path " + reader.path)
                }
                reader.skipAttributeValue()
            }
            value.producerRef = reader.nextTextContent()
        }

        childElementBinders["OJPLocationInformationDelivery"] = ChildElementBinder<ValueHolder> { reader, config, value ->
            value.ojpDelivery =
                config.getTypeAdapter<LocationInformationDeliveryDto>(LocationInformationDeliveryDto::class.java)
                    .fromXml(reader, config, false)
        }

        childElementBinders["OJPTripDelivery"] = ChildElementBinder<ValueHolder> { reader, config, value ->
            value.ojpDelivery = config.getTypeAdapter<TripDeliveryDto>(TripDeliveryDto::class.java).fromXml(reader, config, false)
        }
    }

    override fun fromXml(reader: XmlReader?, config: TikXmlConfig?, isGenericList: Boolean): ServiceDeliveryDto {
        val valueHolder = ValueHolder()
        while (reader?.hasAttribute() == true) {
            val attributeName = reader.nextAttributeName()
            if (config?.exceptionOnUnreadXml() == true && !attributeName.startsWith("xmlns")) {
                throw IOException("Could not map the xml attribute with the name '" + attributeName + "' at path " + reader.path + " to java class. Have you annotated such a field in your java class to map this xml attribute? Otherwise you can turn this error message off with TikXml.Builder().exceptionOnUnreadXml(false).build().")
            }
            reader.skipAttributeValue()
        }

        while (true) {
            if (reader?.hasElement() == true) {
                reader.beginElement()
                val elementName = reader.nextElementName()
                val childElementBinder = childElementBinders[elementName]
                if (childElementBinder != null) {
                    childElementBinder.fromXml(reader, config, valueHolder)
                    reader.endElement()
                } else if (config?.exceptionOnUnreadXml() == true) {
                    throw IOException("Could not map the xml element with the tag name <" + elementName + "> at path '" + reader.path + "' to java class. Have you annotated such a field in your java class to map this xml attribute? Otherwise you can turn this error message off with TikXml.Builder().exceptionOnUnreadXml(false).build().")
                } else {
                    reader.skipRemainingElement()
                }
            } else if (reader?.hasTextContent() == true) {
                if (config?.exceptionOnUnreadXml() == true) {
                    throw IOException("Could not map the xml element's text content at path '" + reader.path + " to java class. Have you annotated such a field in your java class to map the xml element's text content? Otherwise you can turn this error message off with TikXml.Builder().exceptionOnUnreadXml(false).build().")
                }
                reader.skipTextContent()
            } else {
                break
            }
        }
        return ServiceDeliveryDto(
            responseTimestamp = valueHolder.responseTimestamp!!,
            producerRef = valueHolder.producerRef,
            ojpDelivery = valueHolder.ojpDelivery!!
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

    class ValueHolder {
        var responseTimestamp: String? = null
        var producerRef: String? = null
        var ojpDelivery: AbstractDeliveryDto? = null
    }
}