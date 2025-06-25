package ch.opentransportdata.presentation.tir

import ch.opentransportdata.ojp.data.dto.response.GeoPositionDto
import ch.opentransportdata.ojp.data.dto.response.ModeDto
import ch.opentransportdata.ojp.data.dto.response.NameDto
import ch.opentransportdata.ojp.data.dto.response.PlaceDto
import ch.opentransportdata.ojp.data.dto.response.PlaceResultDto
import ch.opentransportdata.ojp.data.dto.response.place.StopPlaceDto
import ch.opentransportdata.ojp.data.dto.response.tir.LegDto
import ch.opentransportdata.ojp.data.dto.response.tir.leg.AttributeDto
import ch.opentransportdata.ojp.data.dto.response.tir.leg.DatedJourneyDto
import ch.opentransportdata.ojp.data.dto.response.tir.leg.LegAlightDto
import ch.opentransportdata.ojp.data.dto.response.tir.leg.LegBoardDto
import ch.opentransportdata.ojp.data.dto.response.tir.leg.LegStartEndDto
import ch.opentransportdata.ojp.data.dto.response.tir.leg.ServiceTimeDto
import ch.opentransportdata.ojp.data.dto.response.tir.leg.TimedLegDto
import ch.opentransportdata.ojp.data.dto.response.tir.leg.TransferLegDto
import ch.opentransportdata.ojp.domain.model.PtMode
import ch.opentransportdata.ojp.domain.model.TransferType
import java.time.Duration
import java.time.LocalDateTime

/**
 * Created by Michael Ruppen on 03.07.2024
 */
object PreviewData {

    val boatLocation = PlaceResultDto(
        place = PlaceDto(
            stopPlace = StopPlaceDto(
                stopPlaceRef = "8507150",
                name = NameDto("Thun (See)"),
                nameSuffix = null,
                privateCodes = emptyList(),
                topographicPlaceRef = null,
                wheelchairAccessible = null,
                lighting = null,
                covered = null
            ),
            name = NameDto("Thun (See) (Thun)"),
            position = GeoPositionDto(longitude = 7.63132, latitude = 46.75411),
            mode = listOf(ModeDto(ptMode = PtMode.WATER, name = null))
        ),
        complete = true,
        probability = 0.8
    )
    val trainLocation = PlaceResultDto(
        place = PlaceDto(
            stopPlace = StopPlaceDto(
                stopPlaceRef = "8571393",
                name = NameDto("Bern, Eigerplatz"),
                nameSuffix = null,
                privateCodes = emptyList(),
                topographicPlaceRef = null,
                wheelchairAccessible = null,
                lighting = null,
                covered = null
            ),
            name = NameDto("Thun (Thun)"),
            position = GeoPositionDto(longitude = 7.62961, latitude = 46.75485),
            mode = listOf(ModeDto(ptMode = PtMode.RAIL, name = null))
        ),
        complete = true,
        probability = 0.8
    )

    val tramLocation = PlaceResultDto(
        place = PlaceDto(
            stopPlace = StopPlaceDto(
                stopPlaceRef = "8571393",
                name = NameDto("Bern, Eigerplatz"),
                nameSuffix = null,
                privateCodes = emptyList(),
                topographicPlaceRef = null,
                wheelchairAccessible = null,
                lighting = null,
                covered = null
            ),
            name = NameDto("Bern, Eigerplatz (Bern)"),
            position = GeoPositionDto(longitude = 7.43099, latitude = 46.94114),
            mode = listOf(ModeDto(ptMode = PtMode.TRAM, name = null))
        ),
        complete = true,
        probability = 0.748
    )

    val busLocation = PlaceResultDto(
        place = PlaceDto(
            stopPlace = StopPlaceDto(
                stopPlaceRef = "8579896",
                name = NameDto("Bern, Hirschengraben"),
                nameSuffix = null,
                privateCodes = emptyList(),
                topographicPlaceRef = null,
                wheelchairAccessible = null,
                lighting = null,
                covered = null
            ),
            name = NameDto("Bern, Hirschengraben (Bern)"),
            position = GeoPositionDto(longitude = 7.43778, latitude = 46.94607),
            mode = listOf(ModeDto(ptMode = PtMode.BUS, name = null))
        ),
        complete = true,
        probability = 0.748
    )

    val transferLeg = LegDto(
        id = "111",
        duration = Duration.parse("PT5M"),
        transferLeg = TransferLegDto(
            transferType = TransferType.WALK,
            legStart = LegStartEndDto(
                stopPointRef = "8500218",
                name = NameDto(text = "Olten"),
                geoPosition = null
            ),
            legEnd = LegStartEndDto(
                stopPointRef = "8500218",
                name = NameDto(text = "Olten"),
                geoPosition = null
            ),
            duration = Duration.parse("PT5M")
        )
    )

    val timedLeg = LegDto(
        id = "222",
        duration = Duration.parse("PT1H10M"),
        timedLeg = TimedLegDto(
            legBoard = LegBoardDto(
                stopPointRef = "ch:1:sloid:10:3:6",
                stopPointName = NameDto(text = "Basel SBB"),
                plannedQuay = NameDto(text = "6"),
                estimatedQuay = NameDto(text = "6"),
                nameSuffix = null,
                serviceDeparture = ServiceTimeDto(
                    timetabledTime = LocalDateTime.now(),//"2024-07-01T16:03:00Z",
                    estimatedTime = LocalDateTime.now().plusMinutes(5)
                ),
                serviceArrival = null,
                order = 1,
                requestStop = null,
                unplannedStop = null,
                notServicedStop = null,
                noBoardingAtStop = null,
                noAlightingAtStop = null,
                expectedDepartureOccupancy = null
            ),
            legIntermediate = null,
            legAlight = LegAlightDto(
                stopPointRef = "ch:1:sloid:218:7:12",
                stopPointName = NameDto(text = "Olten"),
                plannedQuay = NameDto(text = "12"),
                estimatedQuay = null,
                nameSuffix = null,
                serviceArrival = ServiceTimeDto(
                    timetabledTime = LocalDateTime.now().plusHours(1).plusMinutes(10),//"2024-07-01T16:28:00Z",
                    estimatedTime = null
                ),
                serviceDeparture = null,
                order = 1,
                requestStop = null,
                unplannedStop = null,
                notServicedStop = null,
                noBoardingAtStop = null,
                noAlightingAtStop = null
            ),
            service = DatedJourneyDto(
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
                attributes = listOf(
                    AttributeDto(
                        userText = NameDto("Family Coach with play area"),
                        code = "A__FA"
                    ),
                    AttributeDto(
                        userText = NameDto("Quiet zone in 1st class"),
                        code = "A__RZ"
                    ),
                    AttributeDto(
                        userText = NameDto("Free Internet with the SBB FreeSurf app"),
                        code = "A__FS"
                    ),
                ),
                vehicleRef = null,
                situationFullRefWrapper = null,
                cancelled = false,
                deviation = false,
                unplanned = false
            ),
            legTrack = null
        )
    )

    val cancelledTimedLeg = LegDto(
        id = "345",
        duration = Duration.parse("PT1H10M"),
        timedLeg = TimedLegDto(
            legBoard = LegBoardDto(
                stopPointRef = "ch:1:sloid:10:3:6",
                stopPointName = NameDto(text = "Basel SBB"),
                plannedQuay = NameDto(text = "6"),
                estimatedQuay = NameDto(text = "6"),
                nameSuffix = null,
                serviceDeparture = ServiceTimeDto(
                    timetabledTime = LocalDateTime.now(),//"2024-07-01T16:03:00Z",
                    estimatedTime = LocalDateTime.now().plusMinutes(5)
                ),
                serviceArrival = null,
                order = 1,
                requestStop = null,
                unplannedStop = null,
                notServicedStop = true,
                noBoardingAtStop = null,
                noAlightingAtStop = null,
                expectedDepartureOccupancy = null
            ),
            legIntermediate = null,
            legAlight = LegAlightDto(
                stopPointRef = "ch:1:sloid:218:7:12",
                stopPointName = NameDto(text = "Olten"),
                plannedQuay = NameDto(text = "12"),
                estimatedQuay = null,
                nameSuffix = null,
                serviceArrival = ServiceTimeDto(
                    timetabledTime = LocalDateTime.now().plusHours(1).plusMinutes(10),//"2024-07-01T16:28:00Z",
                    estimatedTime = null
                ),
                serviceDeparture = null,
                order = 1,
                requestStop = null,
                unplannedStop = null,
                notServicedStop = true,
                noBoardingAtStop = null,
                noAlightingAtStop = null
            ),
            service = DatedJourneyDto(
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
                attributes = listOf(
                    AttributeDto(
                        userText = NameDto("Place reservation possible"),
                        code = "A___R"
                    ),
                    AttributeDto(
                        userText = NameDto("Family Coach with play area"),
                        code = "A__FA"
                    ),
                    AttributeDto(
                        userText = NameDto("Quiet zone in 1st class"),
                        code = "A__RZ"
                    ),
                    AttributeDto(
                        userText = NameDto("Free Internet with the SBB FreeSurf app"),
                        code = "A__FS"
                    ),
                    AttributeDto(
                        userText = NameDto("Business zone in 1st class"),
                        code = "A__BZ"
                    ),
                    AttributeDto(
                        userText = NameDto("Restaurant"),
                        code = "A__WR"
                    ),
                ),
                vehicleRef = null,
                situationFullRefWrapper = null,
                cancelled = true,
                deviation = true,
                unplanned = false
            ),
            legTrack = null
        )
    )
}