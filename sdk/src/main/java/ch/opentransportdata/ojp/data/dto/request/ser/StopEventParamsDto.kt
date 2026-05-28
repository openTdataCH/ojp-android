package ch.opentransportdata.ojp.data.dto.request.ser

import ch.opentransportdata.ojp.data.dto.OJP_NAME_SPACE
import ch.opentransportdata.ojp.data.dto.converter.DurationSerializer
import ch.opentransportdata.ojp.domain.model.RealtimeData
import ch.opentransportdata.ojp.domain.model.StopEventType
import ch.opentransportdata.ojp.domain.model.StopHierarchy
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName
import java.time.Duration

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
    @XmlSerialName("IncludeAllRestrictedLines", OJP_NAME_SPACE, "")
    val includeAllRestrictedLines: Boolean? = null,

    @XmlElement(true)
    @XmlSerialName("NumberOfResults", OJP_NAME_SPACE, "")
    val numberOfResults: Int? = null,

    @XmlElement(true)
    @XmlSerialName("TimeWindow", OJP_NAME_SPACE, "")
    @Serializable(with = DurationSerializer::class)
    val timeWindow: Duration? = null,

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

    @XmlElement(true)
    @XmlSerialName("IncludePlacesContext", OJP_NAME_SPACE, "")
    val includePlacesContext: Boolean? = null,

    @XmlElement(true)
    @XmlSerialName("IncludeSituationsContext", OJP_NAME_SPACE, "")
    val includeSituationsContext: Boolean? = null,

    @XmlElement(true)
    @XmlSerialName("IncludeStopHierarchy", OJP_NAME_SPACE, "")
    val includeStopHierarchy: StopHierarchy? = null,
)