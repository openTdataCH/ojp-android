package ch.opentransportdata.ojp.data.dto.response

import android.os.Parcelable
import ch.opentransportdata.ojp.data.dto.OJP_NAME_SPACE
import ch.opentransportdata.ojp.data.dto.SIRI_NAME_SPACE
import ch.opentransportdata.ojp.data.dto.SIRI_PREFIX
import ch.opentransportdata.ojp.domain.model.PtMode
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

/**
 * Created by Michael Ruppen on 08.04.2024
 *
 * Serializable and Parcelize annotation is both needed for compose navigation with custom types
 */
@Parcelize
@Serializable
@XmlSerialName("Mode", OJP_NAME_SPACE, "")
data class ModeDto(
    @XmlElement(true)
    @XmlSerialName("PtMode", OJP_NAME_SPACE, "")
    @Serializable
    val ptMode: PtMode,

    @XmlElement(true)
    @XmlSerialName("Name", OJP_NAME_SPACE, "")
    val name: NameDto? = null,

    @XmlElement(true)
    @XmlSerialName("RailSubmode", SIRI_NAME_SPACE, SIRI_PREFIX)
    val railSubmode: String? = null,
    @XmlElement(true)
    @XmlSerialName("BusSubmode", SIRI_NAME_SPACE, SIRI_PREFIX)
    val busSubmode: String? = null,
    @XmlElement(true)
    @XmlSerialName("CoachSubmode", SIRI_NAME_SPACE, SIRI_PREFIX)
    val coachSubmode: String? = null,
    @XmlElement(true)
    @XmlSerialName("MetroSubmode", SIRI_NAME_SPACE, SIRI_PREFIX)
    val metroSubmode: String? = null,
    @XmlElement(true)
    @XmlSerialName("TramSubmode", SIRI_NAME_SPACE, SIRI_PREFIX)
    val tramSubmode: String? = null,
    @XmlElement(true)
    @XmlSerialName("TrolleyBusSubmode", SIRI_NAME_SPACE, SIRI_PREFIX)
    val trolleyBusSubmode: String? = null,
    @XmlElement(true)
    @XmlSerialName("TelecabinSubmode", SIRI_NAME_SPACE, SIRI_PREFIX)
    val telecabinSubmode: String? = null,
    @XmlElement(true)
    @XmlSerialName("FunicularSubmode", SIRI_NAME_SPACE, SIRI_PREFIX)
    val funicularSubmode: String? = null,
    @XmlElement(true)
    @XmlSerialName("WaterSubmode", SIRI_NAME_SPACE, SIRI_PREFIX)
    val waterSubmode: String? = null,
    @XmlElement(true)
    @XmlSerialName("AirSubmode", SIRI_NAME_SPACE, SIRI_PREFIX)
    val airSubmode: String? = null,
    @XmlElement(true)
    @XmlSerialName("TaxiSubmode", SIRI_NAME_SPACE, SIRI_PREFIX)
    val taxiSubmode: String? = null,
    @XmlElement(true)
    @XmlSerialName("SelfDriveSubmode", SIRI_NAME_SPACE, SIRI_PREFIX)
    val selfDriveSubmode: String? = null
) : Parcelable