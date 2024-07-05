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
    val recordedAtTime: String?, //values like 00001-01-01T00:00:00 are returned and can not be parsed to LocalDateTime thus it's a String
    @Element(name = "siri:TextualContent")
    val textualContent: TextualContentDto?
)
