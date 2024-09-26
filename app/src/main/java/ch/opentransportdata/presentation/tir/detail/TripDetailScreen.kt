package ch.opentransportdata.presentation.tir.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsWalk
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.WarningAmber
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import ch.opentransportdata.ojp.data.dto.response.tir.leg.*
import ch.opentransportdata.ojp.data.dto.response.tir.situations.PtSituationDto
import ch.opentransportdata.ojp.data.dto.response.tir.situations.PublishingActionDto
import ch.opentransportdata.ojp.data.dto.response.tir.trips.TripDto
import ch.opentransportdata.presentation.components.Label
import ch.opentransportdata.presentation.components.LabelType
import ch.opentransportdata.presentation.theme.OJPAndroidSDKTheme
import ch.opentransportdata.presentation.tir.PreviewData
import ch.opentransportdata.presentation.utils.icon
import ch.opentransportdata.presentation.utils.toFormattedString
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Created by Michael Ruppen on 12.07.2024
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TripDetailScreen(
    trip: TripDto,
    situations: List<PtSituationDto>?,
    showSituation: (PublishingActionDto) -> Unit,
) {
    val scrollState = rememberScrollState()
    val timedLegs = trip.legs.mapNotNull { it.legType as? TimedLegDto }
    val attributes = timedLegs.flatMap { it.service.attributes ?: emptyList() }.filter { it.icon() != null }.distinct()

    Column(
        modifier = Modifier
            .navigationBarsPadding()
            .verticalScroll(scrollState)
    ) {
        val consideredSituations = situations?.let { trip.getPtSituationsForTrip(it) } ?: emptyList()
        if (trip.hasAnyDisruption) {
            Text(
                modifier = Modifier.padding(start = 16.dp, bottom = 8.dp),
                text = "Disruptions: ",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            FlowRow(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                if (trip.isCancelled) {
                    Label(
                        icon = Icons.Outlined.Cancel,
                        type = LabelType.RED,
                        text = "Cancelled"
                    )
                }

                if (trip.isInfeasible) {
                    Label(
                        icon = Icons.Outlined.WarningAmber,
                        type = LabelType.RED,
                        text = "Trip not feasible"
                    )
                }

                if (timedLegs.any { it.isPartCancelled } && !trip.isCancelled) {
                    Label(
                        icon = Icons.Outlined.HideSource,
                        type = LabelType.RED,
                        text = "Stops skipped"
                    )
                }
                if (timedLegs.any { it.hasAnyPlatformChanges }) {
                    Label(
                        icon = Icons.Outlined.Shuffle,
                        type = LabelType.RED,
                        text = "Platform changes"
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
                    situations = legType.getPtSituationsForLeg(consideredSituations),
                    showSituation = showSituation
                )
            }
        }

        if (attributes.isNotEmpty()) {
            Attributes(attributes = attributes)
        }
        Spacer(modifier = Modifier.height(48.dp))
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun TimedLeg(
    modifier: Modifier = Modifier,
    leg: TimedLegDto,
    duration: Duration?,
    situations: List<PtSituationDto>? = null,
    showSituation: (PublishingActionDto) -> Unit,
) {
    val attributes = leg.service.attributes?.filter { it.icon() != null } ?: emptyList()
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        shape = RoundedCornerShape(16.dp),
        tonalElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .height(IntrinsicSize.Min)
        ) {
            Box(
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
            ) {
                val color = if (leg.isCancelled) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface

                Surface(
                    modifier = Modifier.size(4.dp),
                    shape = CircleShape,
                    color = color,
                    content = {}
                )
                VerticalDivider(
                    modifier = Modifier.padding(start = 1.5.dp, top = 1.dp, bottom = 1.dp),
                    color = color
                )
                Surface(
                    modifier = Modifier
                        .size(4.dp)
                        .align(Alignment.BottomStart),
                    shape = CircleShape,
                    color = color,
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
                        text = duration.toFormattedString(),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                if (attributes.isNotEmpty()) {
                    FlowRow(
                        modifier = Modifier.padding(start = 53.dp),
                        horizontalArrangement = Arrangement.spacedBy(3.dp)
                    ) {
                        attributes.forEach { attribute ->
                            Icon(
                                modifier = Modifier.height(24.dp),
                                painter = painterResource(attribute.icon()!!),
                                contentDescription = null
                            )
                        }
                    }
                }
                if (leg.isCancelled) {
                    Situation(
                        modifier = Modifier.padding(start = 53.dp),
                        icon = Icons.Outlined.Cancel,
                        text = "Cancelled",
                        onSituationClicked = null
                    )
                }

                if (leg.isPartCancelled && !leg.isCancelled) {
                    Situation(
                        modifier = Modifier.padding(start = 53.dp),
                        icon = Icons.Outlined.HideSource,
                        text = "Part cancelled",
                        onSituationClicked = null
                    )
                }
                val publishingActions = situations
                    ?.filter { it.publishingActions?.publishingActions != null }
                    ?.flatMap { it.publishingActions?.publishingActions!! }

                publishingActions?.forEach { action ->
                    Situation(
                        modifier = Modifier.padding(start = 53.dp),
                        icon = Icons.Rounded.WarningAmber, //todo find better solution
                        text = action.passengerInformationAction?.textualContent?.summaryContent?.summaryText ?: "-",
                        onSituationClicked = { showSituation(action) }
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
    onSituationClicked: (() -> Unit)?
) {
    val customModifier = if (onSituationClicked != null) modifier.clickable { onSituationClicked() } else modifier
    Row(
        modifier = customModifier
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
        onSituationClicked?.let {
            Icon(imageVector = Icons.Outlined.ChevronRight, contentDescription = null)
        }

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
                    text = "+${legBoard.serviceDeparture.delay.toMinutes()}´"
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
                    text = "+${legAlight.serviceArrival.delay.toMinutes()}´"
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
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.DirectionsWalk,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface
        )

        Text(
            modifier = modifier,
            text = "Walk ${leg.duration.toMinutes()}min $destinationText",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun TransferLeg(
    modifier: Modifier = Modifier,
    leg: TransferLegDto
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.DirectionsWalk,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface
        )

        Text(
            modifier = Modifier.padding(start = 4.dp),
            text = "Change to ${leg.legEnd.name?.text}",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun Attributes(
    modifier: Modifier = Modifier,
    attributes: List<AttributeDto>
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            Text(
                modifier = Modifier.padding(bottom = 4.dp),
                text = "Legend",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            attributes.forEach {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.width(20.dp),
                        painter = painterResource(it.icon()!!),
                        contentDescription = null
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = it.userText.text,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }

}

@Composable
fun PtSituationDto.icon(): ImageVector {
    return when (this.priority) {
        1 -> Icons.Outlined.FlashOn
        3 -> Icons.Rounded.WarningAmber
        4 -> Icons.Outlined.Info
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
                delayed = false,
                infeasible = false
            ),
            situations = emptyList(),
            showSituation = {}
        )
    }
}