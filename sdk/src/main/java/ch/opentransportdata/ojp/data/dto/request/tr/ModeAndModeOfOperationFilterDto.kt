package ch.opentransportdata.ojp.data.dto.request.tr

import ch.opentransportdata.ojp.data.dto.OJP_NAME_SPACE
import ch.opentransportdata.ojp.data.dto.SIRI_NAME_SPACE
import ch.opentransportdata.ojp.data.dto.SIRI_PREFIX
import ch.opentransportdata.ojp.domain.model.PtMode
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

/**
 * Created by Michael Ruppen on 31.07.2024
 */
@Serializable
@XmlSerialName("ModeAndModeOfOperationFilter", OJP_NAME_SPACE, "")
internal data class ModeAndModeOfOperationFilterDto(
    @XmlElement(true)
    @XmlSerialName("PtMode", OJP_NAME_SPACE, "")
    val ptMode: List<PtMode>?,

    @XmlElement(true)
    @XmlSerialName("Exclude", OJP_NAME_SPACE, "")
    val exclude: Boolean? = null,

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
)
