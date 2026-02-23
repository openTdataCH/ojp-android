package ch.opentransportdata.presentation.tir.result

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import ch.opentransportdata.R
import ch.opentransportdata.ojp.data.dto.response.tir.situations.PublishingActionDto
import ch.opentransportdata.ojp.data.dto.response.tir.trips.TripDto
import ch.opentransportdata.presentation.components.TripResultHeader
import ch.opentransportdata.presentation.lir.name
import ch.opentransportdata.presentation.navigation.TripMap
import ch.opentransportdata.presentation.tir.detail.TripDetailScreen
import ch.opentransportdata.presentation.tir.filter.FilterScreen
import ch.opentransportdata.presentation.utils.FileReader
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by Michael Ruppen on 28.06.2024
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripResultScreen(
    navHostController: NavHostController,
    viewModel: TripResultViewModel = viewModel(),
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    val state = viewModel.state.collectAsState()
    val context = LocalContext.current

    var initialItemsLoaded by remember { mutableStateOf(false) }
    val detailBottomSheet = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var selectedTrip by remember { mutableStateOf<TripDto?>(null) }
    var selectedAction by remember { mutableStateOf<PublishingActionDto?>(null) }
    val showMapText = if (viewModel.state.collectAsState().value.mapData.isEmpty()) "Refine Trip first" else "Show way on map"
    var selectFilters = remember { mutableStateOf(false) }
    val filterBottomSheet = rememberModalBottomSheetState(skipPartiallyExpanded = true)



    if (selectedTrip != null) {
        ModalBottomSheet(
            onDismissRequest = {
                coroutineScope.launch {
                    selectedTrip = null
                    viewModel.resetMapData()
                }
            },
            sheetState = detailBottomSheet,
            dragHandle = { BottomSheetDefaults.DragHandle() },
        ) {
            TripDetailScreen(
                trip = selectedTrip!!,
                situations = state.value.tripDelivery?.responseContext?.situation?.ptSituation?.filter { it.publishingActions != null },
                showSituation = { selectedAction = it },
                requestTripUpdate = { viewModel.requestTripUpdate(it) },
                refineTrip = { id -> viewModel.refineTrip(id) },
                showMapText = showMapText,
                showMap = { id, isZoomed ->
                    val coordinates = viewModel.state.value.mapData.find { it.id == id }
                    if (coordinates != null) {
                        navHostController.navigate(
                            TripMap(
                                coordinates = coordinates.positions,
                                zoom = if (isZoomed) 17.0 else 7.5
                            )
                        )
                    }
                },
                walkingSpeed = viewModel.state.collectAsState().value.walkingSpeed
            )
        }
    }
    if (selectFilters.value) {
        ModalBottomSheet(
            onDismissRequest = {
                coroutineScope.launch {
                    selectFilters.value = false
                    viewModel.requestTrips()
                }
            },
            sheetState = filterBottomSheet,
            dragHandle = { BottomSheetDefaults.DragHandle() },
        ) {
            FilterScreen(
                viewModel.state.collectAsState().value.walkingSpeed,
                onSelectWalkingSpeed = {
                    viewModel.updateWalkingSpeed(it)
                },
                isDirectConnection = state.value.isDirectConnection,
                onCheckDirectConnection = { viewModel.setDirectConnection() },
                isFewerTransfers = state.value.isFewerTransfers,
                onCheckFewerTransfers = { viewModel.setFewerTransfers() },
                vehicleOptions = viewModel.state.collectAsState().value.vehicleOptions,
                onToggleVehicle = { viewModel.toggleVehicle(it) },
                vehicleSubOptions = viewModel.state.collectAsState().value.vehicleSubOptions,
                onToggleSubVehicle = { viewModel.toggleSubVehicle(it) },
                minDuration = viewModel.state.collectAsState().value.minDuration?.toString() ?: "",
                maxDuration = viewModel.state.collectAsState().value.maxDuration?.toString() ?: "",
                minDistance = viewModel.state.collectAsState().value.minDistance?.toString() ?: "",
                maxDistance = viewModel.state.collectAsState().value.maxDistance?.toString() ?: "",
                onMinDistanceChange = { viewModel.setMinDistance(it.toIntOrNull()) },
                onMaxDistanceChange = { viewModel.setMaxDistance(it.toIntOrNull()) },
                onMaxDurationChange = { viewModel.setMaxDuration(it.toLongOrNull()) },
                onMinDurationChange = { viewModel.setMinDuration(it.toLongOrNull()) },
                isBikeTransport = viewModel.state.collectAsState().value.isBikeTransport,
                onCheckBikeTransport = { viewModel.setBikeTransport() }
            )
        }
    }

    //use for testing only
    LaunchedEffect(Unit) {
        val source = FileReader().read(context.resources, R.raw.zuerich_bern_no_issues)
//        viewModel.requestMockTrips(source)
    }

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo }
            .collect { layoutInfo ->
                if (layoutInfo.visibleItemsInfo.isNotEmpty()) {
                    val firstVisibleItemIndex = layoutInfo.visibleItemsInfo.first().index
                    val lastVisibleItemIndex = layoutInfo.visibleItemsInfo.last().index

                    if (initialItemsLoaded && firstVisibleItemIndex < 1 && !state.value.isLoadingPrevious) {
                        viewModel.loadPreviousTrips()
                    }

                    if (listState.isScrollInProgress && !initialItemsLoaded) {
                        initialItemsLoaded = true
                    }

                    if (lastVisibleItemIndex >= layoutInfo.totalItemsCount - 2 && initialItemsLoaded && !state.value.isLoadingNext) {
                        viewModel.loadNextTrips()
                    }
                }
            }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Trip results") },
                navigationIcon = {
                    IconButton(onClick = { navHostController.navigateUp() }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "navigate back")
                    }
                }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            TripResultHeader(
                modifier = Modifier.padding(horizontal = 8.dp),
                originName = viewModel.origin?.place?.placeType?.name() ?: "-",
                destinationName = viewModel.destination?.place?.placeType?.name() ?: "-",
                swapSearch = { viewModel.swapSearch() },
                onSettingsClick = { selectFilters.value = true }
            )
            HorizontalDivider(modifier = Modifier.padding(top = 16.dp))

            if (state.value.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
            LazyColumn(
                modifier = Modifier.weight(1f),
                state = listState,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Loading indicator for previous items
                if (initialItemsLoaded) {
                    item {
                        CircularProgressIndicator(
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                }

                itemsIndexed(
                    items = state.value.tripDelivery?.tripResults ?: emptyList(),
                    key = { index, item -> item.id + index }
                ) { _, item ->
                    TripItem(
                        modifier = Modifier.clickable {
                            selectedTrip = item.trip as TripDto
                        },
                        trip = item.trip as TripDto,
                        responseContextDto = state.value.tripDelivery?.responseContext
                    )
                    HorizontalDivider()
                }

                // Loading indicator for next items
                if (initialItemsLoaded) {
                    item {
                        CircularProgressIndicator(
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                }
            }
        }

        state.value.events.forEach { event ->
            when (event) {
                is TripResultViewModel.Event.ShowSnackBar -> {
                    coroutineScope.launch { snackBarHostState.showSnackbar(message = event.message) }
                }

                is TripResultViewModel.Event.ScrollToFirstTripItem -> {
                    coroutineScope.launch {
                        val scrollItem = if (initialItemsLoaded) event.offset + 1 else 1
                        try {
                            listState.animateScrollToItem(index = scrollItem)
                        } catch (e: Exception) {
                            Log.d("TripResultScreen", "User is still dragging and that as higher priority")
                            delay(2000) //delay the reset so it wont instantly load new items while still dragging
                        }
                        viewModel.resetPreviousItemsCounter()
                    }
                }
            }
            viewModel.eventHandled(event.id)
        }
    }

    if (selectedAction != null) {
        SituationInfoDialog(
            dismissAction = { selectedAction = null },
            summaryText = selectedAction?.passengerInformationAction?.textualContent?.summaryContent?.summaryText ?: "-",
            durationText = selectedAction?.passengerInformationAction?.textualContent?.durationContent?.durationText ?: "-",
            reasonText = selectedAction?.passengerInformationAction?.textualContent?.reasonContent?.reasonText ?: "-",
        )
    }
}

@Composable
private fun SituationInfoDialog(
    modifier: Modifier = Modifier,
    dismissAction: () -> Unit,
    summaryText: String,
    durationText: String,
    reasonText: String
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = dismissAction,
        confirmButton = {
            TextButton(onClick = dismissAction) {
                Text(text = "OK")
            }
        },
        text = {
            Column {
                ListItem(
                    modifier = Modifier.fillMaxWidth(),
                    overlineContent = { Text(text = "Summary") },
                    headlineContent = { Text(text = summaryText) },
                    colors = ListItemDefaults.colors(containerColor = Color.Transparent)
                )
                ListItem(
                    overlineContent = { Text(text = "Duration") },
                    headlineContent = { Text(text = durationText) },
                    colors = ListItemDefaults.colors(containerColor = Color.Transparent)
                )
                ListItem(
                    overlineContent = { Text(text = "Reason") },
                    headlineContent = { Text(text = reasonText) },
                    colors = ListItemDefaults.colors(containerColor = Color.Transparent)
                )
            }
        }
    )
}