package ch.opentransportdata.ojp.data.dto.response.tir.situations

import android.os.Parcelable
import ch.opentransportdata.ojp.data.dto.OJP_NAME_SPACE
import ch.opentransportdata.ojp.data.dto.SIRI_NAME_SPACE
import ch.opentransportdata.ojp.data.dto.SIRI_PREFIX
import ch.opentransportdata.ojp.data.dto.converter.LocalDateTimeSerializer
import ch.opentransportdata.ojp.domain.model.ScopeType
import kotlinx.parcelize.Parcelize
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName
import java.time.LocalDateTime

/**
 * Created by Michael Ruppen on 28.06.2024
 */
@Parcelize
@kotlinx.serialization.Serializable
@XmlSerialName("PtSituation", OJP_NAME_SPACE, "")
data class PtSituationDto(
    @XmlElement(true)
    @XmlSerialName("CreationTime", SIRI_NAME_SPACE, SIRI_PREFIX)
    @kotlinx.serialization.Serializable(with = LocalDateTimeSerializer::class)
    val creationTime: LocalDateTime? = null,

    @XmlElement(true)
    @XmlSerialName("ParticipantRef", SIRI_NAME_SPACE, SIRI_PREFIX)
    val participantRef: String? = null,

    @XmlElement(true)
    @XmlSerialName("SituationNumber", SIRI_NAME_SPACE, SIRI_PREFIX)
    val situationNumber: String,

    @XmlElement(true)
    @XmlSerialName("Version", SIRI_NAME_SPACE, SIRI_PREFIX)
    val version: String? = null,

    @XmlElement(true)
    @XmlSerialName("Source", SIRI_NAME_SPACE, SIRI_PREFIX)
    val source: SourceDto? = null,

    @XmlElement(true)
    @XmlSerialName("ValidityPeriod", SIRI_NAME_SPACE, SIRI_PREFIX)
    val validityPeriod: List<ValidityPeriodDto> = emptyList(),

    @XmlElement(true)
    @XmlSerialName("AlertCause", SIRI_NAME_SPACE, SIRI_PREFIX)
    val alertCause: String? = null,

    @XmlElement(true)
    @XmlSerialName("Priority", SIRI_NAME_SPACE, SIRI_PREFIX)
    val priority: Int? = null,

    @XmlElement(true)
    @XmlSerialName("ScopeType", OJP_NAME_SPACE, "")
    val scopeType: ScopeType? = null,

    @XmlElement(true)
    @XmlSerialName("Language", SIRI_NAME_SPACE, SIRI_PREFIX)
    val language: String? = null,

    @XmlElement(true)
    @XmlSerialName("PublishingActions", SIRI_NAME_SPACE, SIRI_PREFIX)
    val publishingActions: PublishingActionsDto? = null
) : Parcelable