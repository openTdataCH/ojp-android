package ch.opentransportdata.ojp.data.dto.response.tir.situations

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

/**
 * Created by Michael Ruppen on 28.06.2024
 */
@Xml(name = "siri:PublishingActions")
data class PublishingActionsDto(
    @Element(name = "siri:PublishingAction")
    val publishingActions: List<PublishingActionDto>?
)

@Xml(name = "siri:PublishingAction")
data class PublishingActionDto(
    @Element(name = "siri:PassengerInformationAction")
    val passengerInformationAction: PassengerInformationActionDto?
)

@Xml(name = "siri:PassengerInformationAction")
data class PassengerInformationActionDto(
    @PropertyElement(name = "siri:RecordedAtTime")
    val recordedAtTime: String?,
    @Element(name = "siri:TextualContent")
    val textualContent: TextualContentDto?
)
