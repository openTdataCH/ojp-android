package ch.opentransportdata.ojp.data.dto.response.tir.situations

import android.os.Parcelable
import ch.opentransportdata.ojp.data.dto.OJP_NAME_SPACE
import ch.opentransportdata.ojp.data.dto.SIRI_NAME_SPACE
import ch.opentransportdata.ojp.data.dto.SIRI_PREFIX
import ch.opentransportdata.ojp.domain.model.ScopeType
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

/**
 * Created by Deniz Kalem on 27.11.2024
 */
@Parcelize
@Serializable
@XmlSerialName("PublishAtScope", SIRI_NAME_SPACE, SIRI_PREFIX)
data class PublishAtScope(
    @XmlElement(true)
    @XmlSerialName("ScopeType", OJP_NAME_SPACE, "")
    val scopeType: ScopeType? = null,

    @XmlElement(true)
    @XmlSerialName("Affects", SIRI_NAME_SPACE, SIRI_PREFIX)
    val affects: AffectsDto
) : Parcelable