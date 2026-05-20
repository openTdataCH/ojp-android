package ch.opentransportdata.ojp.data.dto.request.ser

import ch.opentransportdata.ojp.data.dto.OJP_NAME_SPACE
import ch.opentransportdata.ojp.domain.model.RealtimeData
import ch.opentransportdata.ojp.domain.model.StopEventType
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

/**
 * Created by Deniz Kalem on 20.05.2026
 */
@Serializable
@XmlSerialName("Params", OJP_NAME_SPACE, "")
internal data class StopEventParamsDto(

    @XmlElement(true)
    val modeFilter: ModeFilterDto? = null,

    @XmlElement(true)
    val lineFilter: LineFilterDto? = null,

    @XmlElement(true)
    val operatorFilter: OperatorFilterDto? = null,

    @XmlElement(true)
    @XmlSerialName("NumberOfResults", OJP_NAME_SPACE, "")
    val numberOfResults: Int? = null,

    @XmlElement(true)
    @XmlSerialName("StopEventType", OJP_NAME_SPACE, "")
    val stopEventType: StopEventType? = null,

    @XmlElement(true)
    @XmlSerialName("IncludePreviousCalls", OJP_NAME_SPACE, "")
    val includePreviousCalls: Boolean? = null,

    @XmlElement(true)
    @XmlSerialName("IncludeOnwardCalls", OJP_NAME_SPACE, "")
    val includeOnwardCalls: Boolean? = null,

    @XmlElement(true)
    @XmlSerialName("IncludeOperatingDays", OJP_NAME_SPACE, "")
    val includeOperatingDays: Boolean? = null,

    @XmlElement(true)
    @XmlSerialName("UseRealtimeData", OJP_NAME_SPACE, "")
    val useRealtimeData: RealtimeData? = null,
)