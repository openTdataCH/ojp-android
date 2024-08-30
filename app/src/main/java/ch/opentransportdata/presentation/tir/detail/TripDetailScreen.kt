package ch.opentransportdata.presentation.tir.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.HideSource
import androidx.compose.material.icons.rounded.WarningAmber
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import ch.opentransportdata.ojp.data.dto.response.tir.leg.*
import ch.opentransportdata.ojp.data.dto.response.tir.situations.PtSituationDto
import ch.opentransportdata.ojp.data.dto.response.tir.trips.TripDto
import ch.opentransportdata.presentation.components.Label
import ch.opentransportdata.presentation.components.LabelType
import ch.opentransportdata.presentation.theme.OJPAndroidSDKTheme
import ch.opentransportdata.presentation.tir.PreviewData
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Created by Michael Ruppen on 12.07.2024
 */
@Composable
fun TripDetailScreen(
    trip: TripDto,
    situations: List<PtSituationDto>?
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .navigationBarsPadding()
            .verticalScroll(scrollState)
    ) {
        val consideredSituations = situations?.let { trip.getPtSituationsForTrip(it) } ?: emptyList()
        if (trip.hasAnyDisruption) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Disruptions: ",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                if (trip.cancelled == true) {
                    Icon(
                        modifier = Modifier
                            .padding(start = 2.dp)
                            .size(20.dp),
                        imageVector = Icons.Outlined.Cancel,
                        contentDescription = "trip is cancelled",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
                if (trip.deviation == true) {
                    Icon(
                        modifier = Modifier
                            .padding(start = 2.dp)
                            .size(20.dp),
                        imageVector = Icons.Outlined.SubdirectoryArrowRight,
                        contentDescription = "trip is redirected",
                        tint = MaterialTheme.colorScheme.error
                    )
                }

                if (trip.infeasible == true) {
                    Icon(
                        modifier = Modifier
                            .padding(start = 2.dp)
                            .size(20.dp),
                        imageVector = Icons.Rounded.WarningAmber,
                        contentDescription = "trip is infeasible",
                        tint = MaterialTheme.colorScheme.error
                    )
                }

                if (trip.legs.mapNotNull { it.legType as? TimedLegDto }.any { it.isPartCancelled }) {
                    Icon(
                        modifier = Modifier
                            .padding(start = 2.dp)
                            .size(20.dp),
                        imageVector = Icons.Rounded.HideSource,
                        contentDescription = "trip is part cancelled",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
                if (consideredSituations.any { it.priority == "1" }) {
                    Icon(
                        modifier = Modifier
                            .padding(start = 2.dp)
                            .size(20.dp),
                        imageVector = Icons.Outlined.FlashOn,
                        contentDescription = "emergency issue",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
                if (consideredSituations.any { it.priority == "3" }) {
                    Icon(
                        modifier = Modifier
                            .padding(start = 2.dp)
                            .size(20.dp),
                        imageVector = Icons.Rounded.WarningAmber,
                        contentDescription = "planned/unplanned issue",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
                if (consideredSituations.any { it.priority == "4" }) {
                    Icon(
                        modifier = Modifier
                            .padding(start = 2.dp)
                            .size(20.dp),
                        imageVector = Icons.Outlined.Info,
                        contentDescription = "general information",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
            HorizontalDivider(modifier = Modifier.padding(bottom = 8.dp))
        }
        trip.legs.forEach { leg ->
            when (val legType = leg.legType) {
                is TransferLegDto -> TransferLeg(modifier = Modifier.padding(all = 16.dp), leg = legType)
                is ContinuousLegDto -> ContinuousLeg(
                    modifier = Modifier.padding(all = 16.dp),
                    leg = legType,
                    isStartLeg = leg == trip.legs.first(),
                    isLastLeg = leg == trip.legs.last(),
                )

                is TimedLegDto -> TimedLeg(
                    leg = legType,
                    duration = leg.duration,
                    situations = legType.getPtSituationsForLeg(consideredSituations)
                )
            }
        }
    }
}

@Composable
private fun TimedLeg(
    modifier: Modifier = Modifier,
    leg: TimedLegDto,
    duration: Duration?,
    situations: List<PtSituationDto>? = null
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        shape = RoundedCornerShape(16.dp),
        tonalElevation = 2.dp //todo: check use 0 or 3 imo
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .height(IntrinsicSize.Min)
        ) {

            Box(
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
            ) {
                Surface(
                    modifier = Modifier.size(4.dp),
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.onSurface,
                    content = {}
                )
                VerticalDivider(
                    modifier = Modifier.padding(start = 1.5.dp, top = 1.dp, bottom = 1.dp),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Surface(
                    modifier = Modifier
                        .size(4.dp)
                        .align(Alignment.BottomStart),
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.onSurface,
                    content = {}
                )
            }

            Column(modifier = Modifier.padding(end = 16.dp)) {
                LegBoard(legBoard = leg.legBoard)
                Text(
                    modifier = Modifier.padding(start = 53.dp),
                    text = "${leg.service.mode.name?.text} ${leg.service.publishedServiceName.text} direction ${leg.service.destinationText?.text}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                duration?.let {
                    Text(
                        modifier = Modifier.padding(start = 53.dp),
                        text = "${duration.toHoursPart()}h ${duration.toMinutesPart()}min",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                if (leg.isCancelled) {
                    Situation(
                        modifier = Modifier.padding(start = 53.dp),
                        icon = Icons.Outlined.Cancel,
                        text = "Cancelled",
                        onSituationClicked = {}
                    )
                }

                if (leg.isPartCancelled) {
                    Situation(
                        modifier = Modifier.padding(start = 53.dp),
                        icon = Icons.Outlined.HideSource,
                        text = "Part cancelled",
                        onSituationClicked = {}
                    )
                }
                val publishingActions = situations?.filter { it.publishingActions?.publishingActions != null }
                    ?.flatMap { it.publishingActions?.publishingActions!! }

                publishingActions?.forEach {
                    Situation(
                        modifier = Modifier.padding(start = 53.dp),
                        icon = Icons.Rounded.WarningAmber, //todo find better solution
                        text = it.passengerInformationAction?.textualContent?.summaryContent?.summaryText ?: "-",
                        onSituationClicked = {}
                    )
                }


                Spacer(modifier = Modifier.height(16.dp))
                LegAlight(legAlight = leg.legAlight)
            }
        }
    }
}

@Composable
private fun Situation(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    text: String,
    onSituationClicked: () -> Unit
) {
    Row(
        modifier = modifier
            .clickable { onSituationClicked() }
            .padding(vertical = 2.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(20.dp),
            imageVector = icon,
            contentDescription = "trip has issues",
            tint = MaterialTheme.colorScheme.error
        )
        Text(
            modifier = Modifier
                .padding(start = 4.dp)
                .weight(1f),
            text = text,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodyMedium
        )
        Icon(imageVector = Icons.Outlined.ChevronRight, contentDescription = null)

    }
}


@Composable
private fun LegBoard(
    modifier: Modifier = Modifier,
    legBoard: LegBoardDto
) {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    val platform = legBoard.estimatedQuay?.text ?: legBoard.plannedQuay?.text

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = legBoard.serviceDeparture.timetabledTime.format(formatter),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurface
        )

        Row(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (legBoard.serviceDeparture.hasDelay) {
                Label(
                    type = LabelType.RED,
                    text = "+ ${legBoard.serviceDeparture.delay.toMinutes()}´"
                )
            }

            Text(
                modifier = Modifier.padding(start = if (legBoard.serviceDeparture.hasDelay) 8.dp else 0.dp),
                text = legBoard.stopPointName.text,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        platform?.let {
            Text(
                text = "Platform $platform",
                style = MaterialTheme.typography.titleSmall,
                color = if (legBoard.isPlatformChanged) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
private fun LegAlight(
    modifier: Modifier = Modifier,
    legAlight: LegAlightDto
) {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    val platform = legAlight.estimatedQuay?.text ?: legAlight.plannedQuay?.text

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = legAlight.serviceArrival.timetabledTime.format(formatter),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurface
        )

        Row(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (legAlight.serviceArrival.hasDelay) {
                Label(
                    type = LabelType.RED,
                    text = "+ ${legAlight.serviceArrival.delay.toMinutes()}´"
                )
            }

            Text(
                modifier = Modifier.padding(start = if (legAlight.serviceArrival.hasDelay) 8.dp else 0.dp),
                text = legAlight.stopPointName.text,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        platform?.let {
            Text(
                text = "Platform $platform",
                style = MaterialTheme.typography.titleSmall,
                color = if (legAlight.isPlatformChanged) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
private fun ContinuousLeg(
    modifier: Modifier = Modifier,
    leg: ContinuousLegDto,
    isStartLeg: Boolean,
    isLastLeg: Boolean,
) {
    val destinationText = when {
        isStartLeg && !leg.legEnd.name?.text.isNullOrBlank() -> "to ${leg.legEnd.name?.text}"
        isLastLeg && !leg.legStart.name?.text.isNullOrBlank() -> "from ${leg.legStart.name?.text}"
        else -> ""
    }
    Text(
        modifier = modifier,
        text = "Walk ${leg.duration.toMinutes()}min $destinationText",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurface
    )
}

@Composable
private fun TransferLeg(
    modifier: Modifier = Modifier,
    leg: TransferLegDto
) {
//    HorizontalDivider()
    Text(
        modifier = modifier,
        text = "Change to ${leg.legEnd.name?.text}",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurface
    )
//    HorizontalDivider()
}

@Composable
fun PtSituationDto.icon(): ImageVector {
    return when (this.priority) {
        "1" -> Icons.Outlined.FlashOn
        "3" -> Icons.Rounded.WarningAmber
        "4" -> Icons.Outlined.Info
        else -> Icons.Rounded.WarningAmber
    }
}

@PreviewLightDark
@PreviewFontScale
@Composable
private fun TripDetailScreenPreview() {
    OJPAndroidSDKTheme {
        TripDetailScreen(
            trip = TripDto(
                id = "1234",
                duration = Duration.parse("PT1H10M"),
                startTime = LocalDateTime.now(),
                endTime = LocalDateTime.now().plusHours(1).plusMinutes(10),
                transfers = 1,
                legs = listOf(
                    PreviewData.timedLeg,
                    PreviewData.transferLeg,
                    PreviewData.cancelledTimedLeg
                ),
                unplanned = null,
                cancelled = true,
                deviation = false,
                delayed = false,
                infeasible = false
            ),
            situations = emptyList()
        )
    }
}