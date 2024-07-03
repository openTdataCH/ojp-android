package ch.opentransportdata.presentation.tir.result

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import ch.opentransportdata.ojp.data.dto.response.tir.TripResponseContextDto
import ch.opentransportdata.ojp.data.dto.response.tir.trips.TripDto
import ch.opentransportdata.presentation.components.Label
import ch.opentransportdata.presentation.components.LabelType
import ch.opentransportdata.presentation.theme.OJPAndroidSDKTheme
import ch.opentransportdata.presentation.tir.PreviewData
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import kotlin.time.Duration

/**
 * Created by Michael Ruppen on 28.06.2024
 */
@Composable
fun TripItem(
    modifier: Modifier = Modifier,
    responseContextDto: TripResponseContextDto? = null, //todo: think about only passing relevant data (if not too much)
    trip: TripDto,
    hasDisruptions: Boolean,
) {
    var isPriceVisible by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(key1 = Unit) {
        coroutineScope.launch {
            delay(2000)
            isPriceVisible = true
        }
    }

    val duration = Duration.parseOrNull(trip.duration)
    val startTime = LocalDateTime
        .ofInstant(trip.firstTimedLeg.legBoard.serviceDeparture.timetabledTimeInstant, ZoneOffset.UTC)
        .format(DateTimeFormatter.ofPattern("HH:mm"))
    val platform = trip.firstTimedLeg.legBoard.estimatedQuay?.text ?: trip.firstTimedLeg.legBoard.plannedQuay.text

    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(all = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = LocalDateTime.parse(trip.startTime).format(DateTimeFormatter.ofPattern("HH:mm")),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = LocalDateTime.parse(trip.endTime).format(DateTimeFormatter.ofPattern("HH:mm")),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 2.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            if (trip.startWithTransferLeg) {
                Text(
                    text = startTime,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    modifier = Modifier.padding(start = 4.dp),
                    text = "${trip.firstTimedLeg.service.publishedServiceName.text} direction ${trip.firstTimedLeg.service.destinationText.text}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.weight(1f))
            }
            Text(
                text = "${duration?.inWholeHours}h ${duration?.inWholeMinutes?.rem(60)}m",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            HorizontalDivider(modifier = Modifier.align(Alignment.Center))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(trip.transfers) {
                    Surface(
                        modifier = Modifier.size(4.dp),
                        color = MaterialTheme.colorScheme.primary,
                        shape = CircleShape,
                        content = {}
                    )
                }
            }
        }

        Row(
            modifier = Modifier.padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Pl. $platform",
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (trip.firstTimedLeg.legBoard.isPlatformChanged) MaterialTheme.colorScheme.onError else MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = "1.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface

                )
                ClassOccupancy(occupancyCount = 1)

                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = "2.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                ClassOccupancy(occupancyCount = 2)
            }
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                if (hasDisruptions) {
                    Label(
                        type = LabelType.RED,
                        icon = Icons.Default.Warning
                    )
                }
                AnimatedVisibility(visible = isPriceVisible) {
                    Label(
                        modifier = Modifier.padding(start = 8.dp),
                        type = LabelType.GREEN,
                        icon = Icons.Default.FavoriteBorder,
                        text = "from CHF 25.20"
                    )
                }
            }
        }

    }
}

@Composable
private fun ClassOccupancy(
    modifier: Modifier = Modifier,
    occupancyCount: Int
) {

    Box(modifier = modifier) {
        Icon(
            modifier = Modifier.size(18.dp),
            imageVector = Icons.Default.Person,
            contentDescription = "second class occupancy",
            tint = MaterialTheme.colorScheme.onSurface
        )
        Icon(
            modifier = Modifier
                .padding(start = 4.dp)
                .size(18.dp),
            imageVector = if (occupancyCount == 2) Icons.Default.Person else Icons.Outlined.Person,
            contentDescription = "second class occupancy",
            tint = MaterialTheme.colorScheme.onSurface
        )
        Icon(
            modifier = Modifier
                .padding(start = 8.dp)
                .size(18.dp),
            imageVector = if (occupancyCount == 3) Icons.Default.Person else Icons.Outlined.Person,
            contentDescription = "second class occupancy",
            tint = MaterialTheme.colorScheme.onSurface
        )
    }
}

@PreviewLightDark
@Composable
private fun TripItemPreview() {
    OJPAndroidSDKTheme {
        TripItem(
            hasDisruptions = true,
            trip = TripDto(
                id = "1234",
                duration = "PT1H",
                startTime = LocalDateTime.now().toString(),
                endTime = LocalDateTime.now().plusHours(1).toString(),
                transfers = 0,
                legs = listOf(
                    PreviewData.transferLeg,
                    PreviewData.timedLeg
                )
            )
        )
    }
}

@PreviewLightDark
@Composable
private fun TripItemSecondPreview() {
    OJPAndroidSDKTheme {
        TripItem(
            hasDisruptions = false,
            trip = TripDto(
                id = "1234",
                duration = "PT1H18M",
                startTime = LocalDateTime.now().toString(),
                endTime = LocalDateTime.now().plusHours(1).plusMinutes(18).toString(),
                transfers = 2,
                legs = listOf(
                    PreviewData.transferLeg,
                    PreviewData.timedLeg
                )
            )
        )
    }
}