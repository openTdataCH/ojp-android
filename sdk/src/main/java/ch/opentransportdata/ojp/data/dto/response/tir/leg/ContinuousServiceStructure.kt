package ch.opentransportdata.ojp.data.dto.response.tir.leg

import android.os.Parcelable
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Xml
import kotlinx.parcelize.Parcelize

/**
 * Created by Nico Brandenberger on 02.07.2025
 */

@Parcelize
@Xml(name = "ContinuousServiceTypeChoice")
data class ContinuousServiceTypeChoiceDto(
    @Element(name = "PersonalService")
    val personalService: PersonalServiceDto?,
    @Element(name = "DatedJourney")
    val datedJourneyDto: DatedJourneyDto?
) : Parcelable

@Parcelize
@Xml(name = "PersonalService")
data class PersonalServiceDto(
    @Element(name = "PersonalMode")
    val personalMode: String,
) : Parcelable