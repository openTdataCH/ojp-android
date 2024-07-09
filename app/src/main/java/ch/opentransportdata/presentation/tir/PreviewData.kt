package ch.opentransportdata.presentation.tir

import ch.opentransportdata.ojp.data.dto.response.ModeDto
import ch.opentransportdata.ojp.data.dto.response.NameDto
import ch.opentransportdata.ojp.data.dto.response.tir.LegDto
import ch.opentransportdata.ojp.data.dto.response.tir.leg.*
import ch.opentransportdata.ojp.domain.model.PtMode
import java.time.LocalDateTime

/**
 * Created by Michael Ruppen on 03.07.2024
 */
object PreviewData {

    val transferLeg = LegDto(
        id = "111",
        duration = "PT5M",
        legType = TransferLegDto(
            legStart = LegStartEndDto(
                stopPointRef = "8500218",
                name = NameDto(text = "Olten")
            ),
            legEnd = LegStartEndDto(
                stopPointRef = "8500218",
                name = NameDto(text = "Olten")
            ),
            duration = "PT5M"
        )
    )

    val timedLeg = LegDto(
        id = "222",
        duration = "PT1H",
        legType = TimedLegDto(
            legBoard = LegBoardDto(
                stopPointRef = "ch:1:sloid:10:3:6",
                stopPointName = NameDto(text = "Basel SBB"),
                plannedQuay = NameDto(text = "6"),
                estimatedQuay = NameDto(text = "6"),
                nameSuffix = null,
                serviceDeparture = ServiceDepartureDto(
                    timetabledTime = LocalDateTime.now(),//"2024-07-01T16:03:00Z",
                    estimatedTime = null
                ),
                serviceArrival = null,
                order = 1
            ),
            legIntermediate = null,
            legAlight = LegAlightDto(
                stopPointRef = "ch:1:sloid:218:7:12",
                stopPointName = NameDto(text = "Olten"),
                plannedQuay = NameDto(text = "12"),
                estimatedQuay = null,
                nameSuffix = null,
                serviceArrival = ServiceDepartureDto(
                    timetabledTime = LocalDateTime.now(),//"2024-07-01T16:28:00Z",
                    estimatedTime = null
                ),
                serviceDeparture = null,
                order = 1
            ),
            service = ServiceDto(
                mode = ModeDto(
                    ptMode = PtMode.RAIL,
                    name = NameDto(text = "Zug")
                ),
                conventionalModeOfOperation = null,
                trainNumber = "2335",
                lineRef = "ojp:91026:C",
                operatorRef = null,
                publicCode = null,
                publishedServiceName = NameDto(text = "IR26"),
                productCategory = null,
                directionRef = "R",
                operatingDayRef = "2024-07-01",
                originStopPointRef = "ch:1:sloid:10:3:6",
                destinationStopPointRef = "ch:1:sloid:218:7:12",
                originText = NameDto(text = "Basel SBB"),
                destinationText = NameDto(text = "Olten"),
                journeyRef = "ch:1:sjyid:100061:2335-001",
                attributes = emptyList()
            ),
            legTrack = null
        )
    )
}