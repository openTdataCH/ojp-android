package ch.opentransportdata.ojp.data.dto.response.tir.situations

import android.os.Parcelable
import ch.opentransportdata.ojp.domain.model.ScopeType
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

/**
 * Created by Michael Ruppen on 28.06.2024
 */
@Parcelize
@Xml(name = "PtSituation")
data class PtSituationDto(
    @PropertyElement(name = "siri:CreationTime")
    val creationTime: LocalDateTime?,
    @PropertyElement(name = "siri:ParticipantRef")
    val participantRef: String?,
    @PropertyElement(name = "siri:SituationNumber")
    val situationNumber: String,
    @PropertyElement(name = "siri:Version")
    val version: String?,
    @Element(name = "siri:Source")
    val source: SourceDto?,
    @Element(name = "siri:ValidityPeriod")
    val validityPeriod: List<ValidityPeriodDto>,
    @PropertyElement(name = "siri:AlertCause")
    val alertCause: String?,
    @PropertyElement(name = "siri:Priority")
    val priority: Int?,
    @PropertyElement(name = "ScopeType")
    val scopeType: ScopeType?,
    @PropertyElement(name = "siri:Language")
    val language: String?,
    @Element(name = "siri:PublishingActions")
    val publishingActions: PublishingActionsDto?
) : Parcelable