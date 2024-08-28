package ch.opentransportdata.presentation.tir.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import ch.opentransportdata.ojp.data.dto.response.tir.leg.*
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
    trip: TripDto
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .navigationBarsPadding()
            .verticalScroll(scrollState)
    ) {
        trip.legs.forEach { leg ->
            when (val legType = leg.legType) {
                is TransferLegDto -> TransferLeg(modifier = Modifier.padding(all = 16.dp), leg = legType)
                is ContinuousLegDto -> ContinuousLeg(
                    modifier = Modifier.padding(all = 16.dp),
                    leg = legType,
                    isStartLeg = leg == trip.legs.first()
                )

                is TimedLegDto -> TimedLeg(leg = legType, duration = leg.duration)
            }
        }
    }
}

@Composable
private fun TimedLeg(
    modifier: Modifier = Modifier,
    leg: TimedLegDto,
    duration: Duration?
) {
    Row(
        modifier = modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
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
                    text = "${duration.toHoursPart()}h ${duration.toMinutesPart()}m",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            LegAlight(legAlight = leg.legAlight)
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
                    text = "+ ${legBoard.serviceDeparture.delay.toMinutes()}"
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
                    text = "+ ${legAlight.serviceArrival.delay.toMinutes()}"
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
    isStartLeg: Boolean
) {
    if (!isStartLeg) HorizontalDivider()
    val destinationText = if (leg.legEnd.name?.text.isNullOrBlank()) "" else "to ${leg.legEnd.name?.text}"
    Text(
        modifier = modifier,
        text = "Walk ${leg.duration.toMinutes()}m $destinationText",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurface
    )
    if (isStartLeg) HorizontalDivider()
}

@Composable
private fun TransferLeg(
    modifier: Modifier = Modifier,
    leg: TransferLegDto
) {
    HorizontalDivider()
    Text(
        modifier = modifier,
        text = "Change to ${leg.legEnd.name?.text}",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurface
    )
    HorizontalDivider()
}

@PreviewLightDark
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
                ),
                unplanned = null,
                cancelled = false,
                deviation = false,
                delayed = false,
                infeasible = false
            )
        )
    }
}