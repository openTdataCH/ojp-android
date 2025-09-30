package ch.opentransportdata.ojp.data.dto.response.tir.situations

import android.os.Parcelable
import ch.opentransportdata.ojp.data.dto.SIRI_NAME_SPACE
import ch.opentransportdata.ojp.data.dto.SIRI_PREFIX
import ch.opentransportdata.ojp.data.dto.converter.LocalDateTimeSerializer
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName
import java.time.LocalDateTime


/**
 * Created by Michael Ruppen on 28.06.2024
 */
@Parcelize
@Serializable
@XmlSerialName("PublishingActions", SIRI_NAME_SPACE, SIRI_PREFIX)
data class PublishingActionsDto(
    @XmlElement(true)
    @XmlSerialName("PublishingAction", SIRI_NAME_SPACE, SIRI_PREFIX)
    val publishingActions: List<PublishingActionDto>? = null
) : Parcelable

@Parcelize
@Serializable
@XmlSerialName("PublishingAction", SIRI_NAME_SPACE, SIRI_PREFIX)
data class PublishingActionDto(
    // TODO Make this mandatory after updating the mock files
    @XmlElement(true)
    @XmlSerialName("PublishAtScope", SIRI_NAME_SPACE, SIRI_PREFIX)
    val publishAtScope: PublishAtScope? = null,

    @XmlElement(true)
    @XmlSerialName("PassengerInformationAction", SIRI_NAME_SPACE, SIRI_PREFIX)
    val passengerInformationAction: PassengerInformationActionDto? = null
) : Parcelable

@Parcelize
@Serializable
@XmlSerialName("PassengerInformationAction", SIRI_NAME_SPACE, SIRI_PREFIX)
data class PassengerInformationActionDto(
    @XmlElement(true)
    @XmlSerialName("RecordedAtTime", SIRI_NAME_SPACE, SIRI_PREFIX)
    @Serializable(with = LocalDateTimeSerializer::class)
    val recordedAtTime: LocalDateTime? = null,

    @XmlElement(true)
    @XmlSerialName("TextualContent", SIRI_NAME_SPACE, SIRI_PREFIX)
    val textualContent: TextualContentDto? = null
) : Parcelable
