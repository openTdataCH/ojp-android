package ch.opentransportdata.presentation.tir.result

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import ch.opentransportdata.ojp.data.dto.response.tir.trips.TripDto
import ch.opentransportdata.presentation.lir.name
import ch.opentransportdata.presentation.theme.OJPAndroidSDKTheme
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
//    originName: String,
//    viaName: String? = null,
//    destinationName: String
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    val state = viewModel.state.collectAsState()
//    val reachedBottom by remember { derivedStateOf { listState.reachedBottom() } } //todo: check if these can be useful to simplify logic
//    val reachedTop by remember { derivedStateOf { listState.reachedTop() } }
    var initialItemsLoaded by remember { mutableStateOf(false) }

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
                    IconButton(onClick = {
                        viewModel.resetTripState()
                        navHostController.navigateUp()
                    }) {
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
                destinationName = viewModel.destination?.place?.placeType?.name() ?: "-"
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

                items(state.value.tripDelivery?.tripResults ?: emptyList(), key = { item -> item.id }) { item ->
                    TripItem(
                        hasDisruptions = false,
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
                        val scrollItem = if (initialItemsLoaded) state.value.previousItemsLoaded else 1
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
}

@Composable
private fun TripResultHeader(
    modifier: Modifier = Modifier,
    originName: String,
    destinationName: String
) {

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        tonalElevation = 1.dp
    ) {
        Column(modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)) {
            Text(
                modifier = Modifier.padding(all = 8.dp),
                text = originName,
                style = MaterialTheme.typography.titleLarge
            )
            HorizontalDivider(modifier = Modifier.padding(horizontal = 8.dp))
            Text(
                modifier = Modifier.padding(all = 8.dp),
                text = destinationName,
                style = MaterialTheme.typography.titleLarge
            )
        }

    }

}

@PreviewLightDark
@Composable
private fun TripResultHeaderPreview() {
    OJPAndroidSDKTheme {
        TripResultHeader(
            originName = "Bern, Eigerplatz",
            destinationName = "Basel SBB"
        )
    }
}