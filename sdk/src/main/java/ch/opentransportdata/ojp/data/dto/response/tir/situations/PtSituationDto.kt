package ch.opentransportdata.ojp.data.dto.response.tir.situations

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

/**
 * Created by Michael Ruppen on 28.06.2024
 */
//todo: check for nullability & which are necessary
@Xml(name = "PtSituation")
data class PtSituationDto(
    @PropertyElement(name = "siri:CreationTime")
    val creationTime: String,
    @PropertyElement(name = "siri:ParticipantRef")
    val participantRef: String,
    @PropertyElement(name = "siri:SituationNumber")
    val situationNumber: String,
    @PropertyElement(name = "siri:Version")
    val version: String,
    @Element(name = "siri:Source")
    val source: SourceDto,
    @Element(name = "siri:ValidityPeriod")
    val validityPeriod: ValidityPeriodDto,
    @PropertyElement(name = "siri:AlertCause")
    val alertCause: String,
    @PropertyElement(name = "siri:UnknownReason")
    val unknownReason: String,
    @PropertyElement(name = "siri:Priority")
    val priority: String,
    @PropertyElement(name = "siri:ScopeType")
    val scopeType: String,
    @PropertyElement(name = "siri:Language")
    val language: String,
    @Element(name = "siri:Affects")
    val affects: AffectsDto,
    @Element(name = "siri:Consequences")
    val consequences: ConsequencesDto,
    @Element(name = "siri:PublishingActions")
    val publishingActions: PublishingActionsDto
)
