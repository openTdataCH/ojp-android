package ch.opentransportdata.ojp.data.dto.response.tir.situations

import android.os.Parcelable
import ch.opentransportdata.ojp.domain.model.ScopeType
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Xml
import kotlinx.parcelize.Parcelize

/**
 * Created by Deniz Kalem on 27.11.2024
 */
@Parcelize
@Xml(name = "siri:PublishAtScope")
data class PublishAtScope(
    @Element(name = "ScopeType")
    val scopeType: ScopeType?,
    @Element(name = "siri:Affects")
    val affects: AffectsDto
) : Parcelable