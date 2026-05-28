package ch.opentransportdata.presentation.feature.stopevent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ch.opentransportdata.ojp.data.dto.response.PlaceResultDto
import ch.opentransportdata.ojp.data.dto.response.ser.StopEventResultDto
import ch.opentransportdata.ojp.domain.model.StopEventType
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Created by Deniz Kalem on 20.05.2026
 */
@Composable
fun StopEventResultScreen(
    modifier: Modifier = Modifier,
    stop: PlaceResultDto,
    viewModel: StopEventResultViewModel = viewModel(),
) {
    val state = viewModel.state.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(stop) {
        viewModel.load(stop, StopEventType.DEPARTURE)
    }

    Scaffold(
        contentWindowInsets = WindowInsets.statusBars,
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = state.value.stopName ?: stop.place?.stopPlace?.name?.text ?: "Stop events",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.size(12.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilterChip(
                    selected = state.value.stopEventType == StopEventType.DEPARTURE,
                    onClick = {
                        if (state.value.stopEventType != StopEventType.DEPARTURE) {
                            viewModel.toggleStopEventType(stop)
                        }
                    },
                    label = { Text("Departures") }
                )
                FilterChip(
                    selected = state.value.stopEventType == StopEventType.ARRIVAL,
                    onClick = {
                        if (state.value.stopEventType != StopEventType.ARRIVAL) {
                            viewModel.toggleStopEventType(stop)
                        }
                    },
                    label = { Text("Arrivals") }
                )
            }

            Spacer(Modifier.size(12.dp))

            when {
                state.value.isLoading -> Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }

                state.value.results.isEmpty() -> Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) { Text("No stop events found") }

                else -> LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(state.value.results, key = { it.id }) { result ->
                        StopEventRow(result, state.value.stopEventType)
                        HorizontalDivider()
                    }
                }
            }
        }
    }

    state.value.events.forEach { event ->
        when (event) {
            is StopEventResultViewModel.Event.ShowSnackBar -> {
                coroutineScope.launch {
                    snackBarHostState.showSnackbar(event.message)
                }
            }
        }
        viewModel.eventHandled(event.id)
    }
}

@Composable
private fun StopEventRow(result: StopEventResultDto, stopEventType: StopEventType) {
    val stopEvent = result.stopEvent
    val call = stopEvent.thisCall.callAtStop
    val service = stopEvent.service

    val time = when (stopEventType) {
        StopEventType.ARRIVAL -> call.serviceArrival?.timetabledTime
        else -> call.serviceDeparture?.timetabledTime
    }
    val estimatedTime = when (stopEventType) {
        StopEventType.ARRIVAL -> call.serviceArrival?.estimatedTime
        else -> call.serviceDeparture?.estimatedTime
    }
    val plannedQuay = call.plannedQuay?.text
    val estimatedQuay = call.estimatedQuay?.text

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = formatTime(time),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            if (estimatedTime != null && estimatedTime != time) {
                Text(
                    text = formatTime(estimatedTime),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
        }

        Spacer(Modifier.width(12.dp))

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text(
                text = service.publishedServiceName.text ?: "",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = service.destinationText?.text ?: service.originText.text ?: "",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
            service.mode.name?.text?.let { Text(it, style = MaterialTheme.typography.bodySmall) }
        }

        if (plannedQuay != null) {
            Text(
                text = "Pl. ${estimatedQuay ?: plannedQuay}",
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

private fun formatTime(time: LocalDateTime?): String {
    return time?.format(timeFormatter) ?: "--:--"
}