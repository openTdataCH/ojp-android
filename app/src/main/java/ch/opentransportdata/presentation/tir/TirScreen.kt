package ch.opentransportdata.presentation.tir

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ch.opentransportdata.presentation.components.LocationInputRow
import ch.opentransportdata.presentation.lir.name
import ch.opentransportdata.presentation.navigation.TripResults
import ch.opentransportdata.presentation.theme.OJPAndroidSDKTheme
import kotlinx.coroutines.launch

/**
 * Created by Michael Ruppen on 25.04.2024
 */
@Composable
fun TirScreenComposable(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    viewModel: TirViewModel = viewModel()
) {
    viewModel.initLocationTracker(LocalContext.current)

    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val state = viewModel.state.collectAsState()

    Scaffold(
        snackbarHost = {
            SnackbarHost(modifier = Modifier.imePadding(), hostState = snackBarHostState)
        }
    ) { innerPaddings ->
        Column(
            modifier = modifier
                .padding(innerPaddings)
                .fillMaxSize(),
        ) {
            Header(
                modifier = Modifier.padding(16.dp),
                requestLocation = { isOrigin, isDestination -> viewModel.getCurrentLocation(isOrigin, isDestination) },
                origin = state.value.origin,
                originTextValueChanged = { viewModel.fetchLocations(it, isOrigin = true) },
                destination = state.value.destination,
                destinationTextValueChanged = { viewModel.fetchLocations(it, isDestination = true) },
                via = state.value.via,
                viaTextValueChanged = { viewModel.fetchLocations(it, isVia = true) },
                updateFocus = { origin, via, destination -> viewModel.updateFocus(origin, via, destination) },
            )
            if (state.value.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .size(20.dp),
                    strokeWidth = 2.dp,
                )
            } else {
                LazyColumn {
                    items(
                        items = state.value.results,
                        key = { item -> item.place.name.text + item.place.position.longitude + item.place.position.latitude + item.distance }
                    ) { item ->
                        ListItem(
                            modifier = Modifier.clickable { viewModel.onLocationSelected(item) },
                            headlineContent = {
                                Text(
                                    text = item.place.placeType?.name() ?: "undef",
                                    style = MaterialTheme.typography.titleSmall
                                )
                            }
                        )
                        if (state.value.results.last() != item) {
                            HorizontalDivider(modifier = Modifier.padding(start = 16.dp))
                        }
                    }
                }
            }
        }
    }

    state.value.events.forEach { event ->
        when (event) {
            is TirViewModel.Event.ShowSnackBar -> {
                coroutineScope.launch {
                    snackBarHostState.showSnackbar(message = event.message)
                }
            }

            is TirViewModel.Event.RequestTrip -> {
                navHostController.navigate(
                    TripResults(
                        origin = event.origin,
                        via = event.via,
                        destination = event.destination
                    )
                )
                viewModel.resetData()
            }
        }
        viewModel.eventHandled(event.id)
    }
}

@Composable
private fun Header(
    modifier: Modifier = Modifier,
    requestLocation: (Boolean, Boolean) -> Unit,
    origin: TirViewModel.TextInput,
    originTextValueChanged: (String) -> Unit,
    destination: TirViewModel.TextInput,
    destinationTextValueChanged: (String) -> Unit,
    via: TirViewModel.TextInput,
    viaTextValueChanged: (String) -> Unit,
    updateFocus: (Boolean, Boolean, Boolean) -> Unit
) {
    Column(
        modifier = modifier,
//        horizontalAlignment = Alignment.End
    ) {
        LocationInputRow(
            requestLocation = { requestLocation(true, false) },
            textInputValue = origin.textInputValue,
            onTextValueChange = originTextValueChanged,
            supportingText = "From",
            hasFocus = origin.hasFocus,
            onFocusChange = { updateFocus(true, false, false) },
            isLocationSelected = origin.selectedLocation != null,
            onClearInputClicked = { originTextValueChanged("") }
        )

        LocationInputRow(
            modifier = Modifier.padding(start = 72.dp, top = 16.dp, bottom = 16.dp),
            isLocationButtonEnabled = false,
            requestLocation = {},
            textInputValue = via.textInputValue,
            onTextValueChange = viaTextValueChanged,
            supportingText = "Via",
            hasFocus = via.hasFocus,
            onFocusChange = { updateFocus(false, true, false) },
            isLocationSelected = via.selectedLocation != null,
            onClearInputClicked = { viaTextValueChanged("") }
        )

        LocationInputRow(
            requestLocation = { requestLocation(false, true) },
            textInputValue = destination.textInputValue,
            onTextValueChange = destinationTextValueChanged,
            supportingText = "To",
            hasFocus = destination.hasFocus,
            onFocusChange = { updateFocus(false, false, true) },
            isLocationSelected = destination.selectedLocation != null,
            onClearInputClicked = { destinationTextValueChanged("") }
        )
    }

}

@PreviewLightDark
@Composable
private fun TirScreenPreview() {
    OJPAndroidSDKTheme {
        TirScreenComposable(navHostController = rememberNavController())
    }
}