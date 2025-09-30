package ch.opentransportdata.ojp.data.dto.request.lir

import ch.opentransportdata.ojp.data.dto.OJP_NAME_SPACE
import ch.opentransportdata.ojp.data.dto.SIRI_NAME_SPACE
import ch.opentransportdata.ojp.data.dto.SIRI_PREFIX
import ch.opentransportdata.ojp.data.dto.converter.LocalDateTimeSerializer
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName
import java.time.LocalDateTime


/**
 * Created by Michael Ruppen on 08.04.2024
 */
@kotlinx.serialization.Serializable
@XmlSerialName("OJPLocationInformationRequest", OJP_NAME_SPACE, /* default ns */ "")
internal data class LocationInformationRequestDto(

    @XmlElement(true)
    @XmlSerialName("RequestTimestamp", SIRI_NAME_SPACE, SIRI_PREFIX)
    @kotlinx.serialization.Serializable(with = LocalDateTimeSerializer::class)
    val requestTimestamp: LocalDateTime,

    @XmlElement(true)
    @XmlSerialName("InitialInput", OJP_NAME_SPACE, "")
    val initialInput: InitialInputDto,

    @XmlElement(true)
    @XmlSerialName("Restrictions", OJP_NAME_SPACE, "")
    val restrictions: RestrictionsDto
)