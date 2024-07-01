package ch.opentransportdata.ojp.data.dto.adapter

import ch.opentransportdata.ojp.data.dto.response.tir.trips.TripDto
import ch.opentransportdata.ojp.data.dto.response.tir.TripResultDto
import ch.opentransportdata.ojp.data.dto.response.tir.trips.AbstractTripDto
import ch.opentransportdata.ojp.data.dto.response.tir.trips.TripSummaryDto
import com.tickaroo.tikxml.TikXmlConfig
import com.tickaroo.tikxml.XmlReader
import com.tickaroo.tikxml.XmlWriter
import com.tickaroo.tikxml.typeadapter.ChildElementBinder
import com.tickaroo.tikxml.typeadapter.TypeAdapter
import java.io.IOException

/**
 * Created by Michael Ruppen on 01.07.2024
 */
class TripResultAdapter : TypeAdapter<TripResultDto?> {
    private val childElementBinders: MutableMap<String, ChildElementBinder<ValueHolder>> = HashMap()

    init {
        childElementBinders["Id"] = ChildElementBinder<ValueHolder> { reader, config, value ->
            while (reader.hasAttribute()) {
                val attributeName = reader.nextAttributeName()
                if (config.exceptionOnUnreadXml() && !attributeName.startsWith("xmlns")) {
                    throw IOException("Unread attribute '" + attributeName + "' at path " + reader.path)
                }
                reader.skipAttributeValue()
            }
            value.id = reader.nextTextContent()
        }
        childElementBinders["Trip"] = ChildElementBinder<ValueHolder> { reader, config, value ->
            value.trip = config.getTypeAdapter<TripDto>(TripDto::class.java).fromXml(reader, config, false)
        }

        childElementBinders["TripSummary"] = ChildElementBinder<ValueHolder> { reader, config, value ->
            value.trip = config.getTypeAdapter<TripSummaryDto>(TripSummaryDto::class.java).fromXml(reader, config, false)
        }
    }

    @Throws(IOException::class)
    override fun fromXml(reader: XmlReader, config: TikXmlConfig, isGenericList: Boolean): TripResultDto {
        val value = ValueHolder()
        while (reader.hasAttribute()) {
            val attributeName = reader.nextAttributeName()
            if (config.exceptionOnUnreadXml() && !attributeName.startsWith("xmlns")) {
                throw IOException("Could not map the xml attribute with the name '" + attributeName + "' at path " + reader.path + " to java class. Have you annotated such a field in your java class to map this xml attribute? Otherwise you can turn this error message off with TikXml.Builder().exceptionOnUnreadXml(false).build().")
            }
            reader.skipAttributeValue()
        }
        while (true) {
            if (reader.hasElement()) {
                reader.beginElement()
                val elementName = reader.nextElementName()
                val childElementBinder = childElementBinders[elementName]
                if (childElementBinder != null) {
                    childElementBinder.fromXml(reader, config, value)
                    reader.endElement()
                } else if (config.exceptionOnUnreadXml()) {
                    throw IOException("Could not map the xml element with the tag name <" + elementName + "> at path '" + reader.path + "' to java class. Have you annotated such a field in your java class to map this xml attribute? Otherwise you can turn this error message off with TikXml.Builder().exceptionOnUnreadXml(false).build().")
                } else {
                    reader.skipRemainingElement()
                }
            } else if (reader.hasTextContent()) {
                if (config.exceptionOnUnreadXml()) {
                    throw IOException("Could not map the xml element's text content at path '" + reader.path + " to java class. Have you annotated such a field in your java class to map the xml element's text content? Otherwise you can turn this error message off with TikXml.Builder().exceptionOnUnreadXml(false).build().")
                }
                reader.skipTextContent()
            } else {
                break
            }
        }
        return TripResultDto(value.id!!, value.trip!!)
    }

    @Throws(IOException::class)
    override fun toXml(
        writer: XmlWriter?,
        config: TikXmlConfig?,
        value: TripResultDto?,
        overridingXmlElementTagName: String?
    ) {
        value?.let {
            when (overridingXmlElementTagName == null) {
                true -> writer?.beginElement("TripResult")
                else -> writer?.beginElement(overridingXmlElementTagName)
            }

            writer?.beginElement("Id")
            writer?.textContent(it.id)
            writer?.endElement()

            when (value.trip) {
                is TripDto -> {
                    config?.getTypeAdapter<TripDto>(TripDto::class.java)?.toXml(writer, config, value.trip, "Trip")
                }

                is TripSummaryDto -> {
                    config?.getTypeAdapter<TripSummaryDto>(TripSummaryDto::class.java)
                        ?.toXml(writer, config, value.trip, "TripSummary")
                }
            }

            writer?.endElement()
        }
    }

    internal class ValueHolder {
        var id: String? = null
        var trip: AbstractTripDto? = null
    }
}
