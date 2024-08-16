package ch.opentransportdata.ojp.data.dto.response.tir.situations

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

/**
 * Created by Michael Ruppen on 28.06.2024
 */
@Xml(name = "siri:TextualContent")
data class TextualContentDto(
    @Element(name = "siri:SummaryContent")
    val summaryContent: SummaryContentDto,
    @Element(name = "siri:ReasonContent")
    val reasonContent: ReasonContentDto?,
    @Element(name = "siri:DescriptionContent")
    val descriptionContent: List<DescriptionContent>?,
    @Element(name = "siri:ConsequenceContent")
    val consequenceContent: List<ConsequenceContentDto>?,
    @Element(name = "siri:RecommendationContent")
    val recommendationContent: List<RecommendationContentDto>?,
    @Element(name = "siri:DurationContent")
    val durationContent: DurationContentDto?,
    @Element(name = "siri:RemarkContent")
    val remarkContent: List<RemarkContent>?
)

@Xml(name = "siri:SummaryContent")
data class SummaryContentDto(
    @PropertyElement(name = "siri:SummaryText")
    val summaryText: String?
)

@Xml(name = "siri:ReasonContent")
data class ReasonContentDto(
    @PropertyElement(name = "siri:ReasonText")
    val reasonText: String?
)

@Xml(name = "siri:ConsequenceContent")
data class ConsequenceContentDto(
    @PropertyElement(name = "siri:ConsequenceText")
    val consequenceText: String?
)

@Xml(name = "siri:RecommendationContent")
data class RecommendationContentDto(
    @PropertyElement(name = "siri:RecommendationText")
    val recommendationText: String?
)

@Xml(name = "siri:DurationContent")
data class DurationContentDto(
    @PropertyElement(name = "siri:DurationText")
    val durationText: String?
)

@Xml(name = "siri:DescriptionContent")
data class DescriptionContent(
    @PropertyElement(name = "siri:DescriptionText")
    val descriptionText: String?
)

@Xml(name = "siri:RemarkContent")
data class RemarkContent(
    @PropertyElement(name = "siri:Remark")
    val remark: String?
)