package ch.opentransportdata.ojp.data.dto.response.tir.situations

import android.os.Parcelable
import ch.opentransportdata.ojp.data.dto.SIRI_NAME_SPACE
import ch.opentransportdata.ojp.data.dto.SIRI_PREFIX
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

/**
 * Created by Michael Ruppen on 28.06.2024
 */
@Parcelize
@Serializable
@XmlSerialName("TextualContent", SIRI_NAME_SPACE, SIRI_PREFIX)
data class TextualContentDto(
    @XmlElement(true)
    @XmlSerialName("SummaryContent", SIRI_NAME_SPACE, SIRI_PREFIX)
    val summaryContent: SummaryContentDto,

    @XmlElement(true)
    @XmlSerialName("ReasonContent", SIRI_NAME_SPACE, SIRI_PREFIX)
    val reasonContent: ReasonContentDto? = null,

    @XmlElement(true)
    @XmlSerialName("DescriptionContent", SIRI_NAME_SPACE, SIRI_PREFIX)
    val descriptionContent: List<DescriptionContent>? = null,

    @XmlElement(true)
    @XmlSerialName("ConsequenceContent", SIRI_NAME_SPACE, SIRI_PREFIX)
    val consequenceContent: List<ConsequenceContentDto>? = null,

    @XmlElement(true)
    @XmlSerialName("RecommendationContent", SIRI_NAME_SPACE, SIRI_PREFIX)
    val recommendationContent: List<RecommendationContentDto>? = null,

    @XmlElement(true)
    @XmlSerialName("DurationContent", SIRI_NAME_SPACE, SIRI_PREFIX)
    val durationContent: DurationContentDto? = null,

    @XmlElement(true)
    @XmlSerialName("RemarkContent", SIRI_NAME_SPACE, SIRI_PREFIX)
    val remarkContent: List<RemarkContent>? = null,

    @XmlElement(true)
    @XmlSerialName("InfoLink", SIRI_NAME_SPACE, SIRI_PREFIX)
    val infoLink: List<InfoLink>? = null
) : Parcelable

@Parcelize
@Serializable
@XmlSerialName("SummaryContent", SIRI_NAME_SPACE, SIRI_PREFIX)
data class SummaryContentDto(
    @XmlElement(true)
    @XmlSerialName("SummaryText", SIRI_NAME_SPACE, SIRI_PREFIX)
    val summaryText: String? = null
) : Parcelable

@Parcelize
@Serializable
@XmlSerialName("ReasonContent", SIRI_NAME_SPACE, SIRI_PREFIX)
data class ReasonContentDto(
    @XmlElement(true)
    @XmlSerialName("ReasonText", SIRI_NAME_SPACE, SIRI_PREFIX)
    val reasonText: String? = null
) : Parcelable

@Parcelize
@Serializable
@XmlSerialName("ConsequenceContent", SIRI_NAME_SPACE, SIRI_PREFIX)
data class ConsequenceContentDto(
    @XmlElement(true)
    @XmlSerialName("ConsequenceText", SIRI_NAME_SPACE, SIRI_PREFIX)
    val consequenceText: String? = null
) : Parcelable

@Parcelize
@Serializable
@XmlSerialName("RecommendationContent", SIRI_NAME_SPACE, SIRI_PREFIX)
data class RecommendationContentDto(
    @XmlElement(true)
    @XmlSerialName("RecommendationText", SIRI_NAME_SPACE, SIRI_PREFIX)
    val recommendationText: String? = null
) : Parcelable

@Parcelize
@Serializable
@XmlSerialName("DurationContent", SIRI_NAME_SPACE, SIRI_PREFIX)
data class DurationContentDto(
    @XmlElement(true)
    @XmlSerialName("DurationText", SIRI_NAME_SPACE, SIRI_PREFIX)
    val durationText: String? = null
) : Parcelable

@Parcelize
@Serializable
@XmlSerialName("DescriptionContent", SIRI_NAME_SPACE, SIRI_PREFIX)
data class DescriptionContent(
    @XmlElement(true)
    @XmlSerialName("DescriptionText", SIRI_NAME_SPACE, SIRI_PREFIX)
    val descriptionText: String? = null
) : Parcelable

@Parcelize
@Serializable
@XmlSerialName("RemarkContent", SIRI_NAME_SPACE, SIRI_PREFIX)
data class RemarkContent(
    @XmlElement(true)
    @XmlSerialName("Remark", SIRI_NAME_SPACE, SIRI_PREFIX)
    val remark: String? = null
) : Parcelable

@Parcelize
@Serializable
@XmlSerialName("InfoLink", SIRI_NAME_SPACE, SIRI_PREFIX)
data class InfoLink(
    @XmlElement(true)
    @XmlSerialName("Uri", SIRI_NAME_SPACE, SIRI_PREFIX)
    val uri: String,

    @XmlElement(true)
    @XmlSerialName("Label", SIRI_NAME_SPACE, SIRI_PREFIX)
    val label: String? = null
) : Parcelable