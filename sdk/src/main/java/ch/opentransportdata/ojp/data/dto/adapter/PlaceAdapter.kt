package ch.opentransportdata.ojp.data.dto.adapter

import ch.opentransportdata.ojp.data.dto.response.GeoPositionDto
import ch.opentransportdata.ojp.data.dto.response.ModeDto
import ch.opentransportdata.ojp.data.dto.response.NameDto
import ch.opentransportdata.ojp.data.dto.response.PlaceDto
import ch.opentransportdata.ojp.data.dto.response.place.AbstractPlaceDto
import ch.opentransportdata.ojp.data.dto.response.place.AddressDto
import ch.opentransportdata.ojp.data.dto.response.place.StopPlaceDto
import com.tickaroo.tikxml.TikXmlConfig
import com.tickaroo.tikxml.XmlReader
import com.tickaroo.tikxml.XmlWriter
import com.tickaroo.tikxml.typeadapter.ChildElementBinder
import com.tickaroo.tikxml.typeadapter.TypeAdapter
import java.io.IOException


/**
 * Created by Michael Ruppen on 15.05.2024
 *
 * Adapter to map all possible place objects correctly
 */

//todo: implement tests
internal class PlaceAdapter : TypeAdapter<PlaceDto> {

    private val childElementBinders: HashMap<String, ChildElementBinder<ValueHolder>> = hashMapOf()

    init {
        childElementBinders["StopPlace"] = ChildElementBinder<ValueHolder> { reader, config, value ->
            value.placeType = config.getTypeAdapter<StopPlaceDto>(StopPlaceDto::class.java).fromXml(reader, config, false)
        }

        childElementBinders["Address"] = ChildElementBinder<ValueHolder> { reader, config, value ->
            value.placeType = config.getTypeAdapter<AddressDto>(AddressDto::class.java).fromXml(reader, config, false)
        }

        childElementBinders["Name"] = ChildElementBinder<ValueHolder> { reader, config, value ->
            value.name = config.getTypeAdapter<NameDto>(NameDto::class.java).fromXml(reader, config, false)
        }

        childElementBinders["GeoPosition"] = ChildElementBinder<ValueHolder> { reader, config, value ->
            value.positionDto = config.getTypeAdapter<GeoPositionDto>(GeoPositionDto::class.java).fromXml(reader, config, false)
        }

        childElementBinders["Mode"] = ChildElementBinder<ValueHolder> { reader, config, value ->
            if (value.modes == null) {
                value.modes = mutableListOf()
            }
            val mode = config.getTypeAdapter<ModeDto>(ModeDto::class.java).fromXml(reader, config, true)
            value.modes?.add(mode)

        }
    }

    override fun fromXml(reader: XmlReader?, config: TikXmlConfig?, isGenericList: Boolean): PlaceDto {
        val valueHolder = ValueHolder()
        while (reader?.hasAttribute() == true) {
            val attributeName = reader.nextAttributeName()
            if (config!!.exceptionOnUnreadXml() && !attributeName.startsWith("xmlns")) {
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
                } else if (config!!.exceptionOnUnreadXml()) {
                    throw IOException("Could not map the xml element with the tag name <" + elementName + "> at path '" + reader.path + "' to java class. Have you annotated such a field in your java class to map this xml attribute? Otherwise you can turn this error message off with TikXml.Builder().exceptionOnUnreadXml(false).build().")
                } else {
                    reader.skipRemainingElement()
                }
            } else if (reader?.hasTextContent() == true) {
                if (config!!.exceptionOnUnreadXml()) {
                    throw IOException("Could not map the xml element's text content at path '" + reader.path + " to java class. Have you annotated such a field in your java class to map the xml element's text content? Otherwise you can turn this error message off with TikXml.Builder().exceptionOnUnreadXml(false).build().")
                }
                reader.skipTextContent()
            } else {
                break
            }
        }

        return PlaceDto(
            placeType = valueHolder.placeType,
            name = valueHolder.name!!,
            position = valueHolder.positionDto!!,
            mode = valueHolder.modes
        )
    }

    override fun toXml(
        writer: XmlWriter?,
        config: TikXmlConfig?,
        value: PlaceDto?,
        overridingXmlElementTagName: String?
    ) {
        value?.let {
            when (overridingXmlElementTagName == null) {
                true -> writer?.beginElement("Place")
                else -> writer?.beginElement(overridingXmlElementTagName)
            }

            when (value.placeType) {
                is StopPlaceDto -> {
                    config?.getTypeAdapter<StopPlaceDto>(StopPlaceDto::class.java)
                        ?.toXml(writer, config, value.placeType, "StopPlace")
                }

                is AddressDto -> {
                    config?.getTypeAdapter<AddressDto>(AddressDto::class.java)
                        ?.toXml(writer, config, value.placeType, "Address")
                }
            }

            config?.getTypeAdapter<NameDto>(NameDto::class.java)?.toXml(writer, config, value.name, "Name")
            config?.getTypeAdapter<GeoPositionDto>(GeoPositionDto::class.java)
                ?.toXml(writer, config, value.position, "GeoPosition")

            value.mode?.forEach {
                config?.getTypeAdapter<ModeDto>(ModeDto::class.java)?.toXml(writer, config, it, "Mode")
            }
            writer?.endElement()
        }
    }

    class ValueHolder {
        var placeType: AbstractPlaceDto? = null
        var name: NameDto? = null
        var positionDto: GeoPositionDto? = null
        var modes: MutableList<ModeDto>? = null
    }
}
