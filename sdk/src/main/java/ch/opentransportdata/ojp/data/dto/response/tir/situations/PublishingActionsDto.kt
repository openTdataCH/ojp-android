package ch.opentransportdata.ojp.data.dto.response.tir.situations

import android.os.Parcelable
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

/**
 * Created by Michael Ruppen on 28.06.2024
 */
@Parcelize
@Xml(name = "siri:PublishingActions")
data class PublishingActionsDto(
    @Element(name = "siri:PublishingAction")
    val publishingActions: List<PublishingActionDto>?
) : Parcelable

@Parcelize
@Xml(name = "siri:PublishingAction")
data class PublishingActionDto(
    @Element(name = "siri:PassengerInformationAction")
    val passengerInformationAction: PassengerInformationActionDto?
) : Parcelable

@Parcelize
@Xml(name = "siri:PassengerInformationAction")
data class PassengerInformationActionDto(
    @PropertyElement(name = "siri:RecordedAtTime")
    val recordedAtTime: LocalDateTime?,
    @Element(name = "siri:TextualContent")
    val textualContent: TextualContentDto?
) : Parcelable
