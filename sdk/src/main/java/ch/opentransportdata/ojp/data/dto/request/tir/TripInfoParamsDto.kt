package ch.opentransportdata.ojp.data.dto.request.tir

import ch.opentransportdata.ojp.data.dto.OJP_NAME_SPACE
import ch.opentransportdata.ojp.domain.model.RealtimeData
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

/**
 * Created by Deniz Kalem on 25.03.2026
 */
@Serializable
@XmlSerialName("Params", OJP_NAME_SPACE, "")
internal data class TripInfoParamsDto(

    @XmlElement(true)
    @XmlSerialName("UseRealtimeData", OJP_NAME_SPACE, "")
    val useRealtimeData: RealtimeData? = null,

    @XmlElement(true)
    @XmlSerialName("IncludeCalls", OJP_NAME_SPACE, "")
    val includeCalls: Boolean? = null,

    @XmlElement(true)
    @XmlSerialName("IncludePosition", OJP_NAME_SPACE, "")
    val includePosition: Boolean? = null,

    @XmlElement(true)
    @XmlSerialName("IncludeService", OJP_NAME_SPACE, "")
    val includeService: Boolean? = null,

    @XmlElement(true)
    @XmlSerialName("IncludeTrackSections", OJP_NAME_SPACE, "")
    val includeTrackSections: Boolean? = null,

    @XmlElement(true)
    @XmlSerialName("IncludeTrackProjection", OJP_NAME_SPACE, "")
    val includeTrackProjection: Boolean? = null,

    @XmlElement(true)
    @XmlSerialName("IncludePlacesContext", OJP_NAME_SPACE, "")
    val includePlacesContext: Boolean? = null,

    @XmlElement(true)
    @XmlSerialName("IncludeFormation", OJP_NAME_SPACE, "")
    val includeFormation: Boolean? = null,

    @XmlElement(true)
    @XmlSerialName("IncludeSituationsContext", OJP_NAME_SPACE, "")
    val includeSituationsContext: Boolean? = null,
)