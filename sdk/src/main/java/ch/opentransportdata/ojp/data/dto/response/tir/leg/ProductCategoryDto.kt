package ch.opentransportdata.ojp.data.dto.response.tir.leg

import android.os.Parcelable
import ch.opentransportdata.ojp.data.dto.OJP_NAME_SPACE
import ch.opentransportdata.ojp.data.dto.response.NameDto
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

/**
 * Created by Michael Ruppen on 28.06.2024
 */
@Parcelize
@Serializable
@XmlSerialName("ProductCategory", OJP_NAME_SPACE, "")
data class ProductCategoryDto(
    @XmlElement(true)
    @XmlSerialName("Name", OJP_NAME_SPACE, "")
    val name: NameDto? = null,

    @XmlElement(true)
    @XmlSerialName("ShortName", OJP_NAME_SPACE, "")
    val shortName: NameDto? = null,

    @XmlElement(true)
    @XmlSerialName("ProductCategoryRef", OJP_NAME_SPACE, "")
    val productCategoryRef: String? = null
) : Parcelable