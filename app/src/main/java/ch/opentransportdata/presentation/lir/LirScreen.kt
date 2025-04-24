package ch.opentransportdata.presentation.lir

import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
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
import ch.opentransportdata.ojp.data.dto.response.place.AbstractPlaceDto
import ch.opentransportdata.ojp.data.dto.response.place.AddressDto
import ch.opentransportdata.ojp.data.dto.response.place.StopPlaceDto
import ch.opentransportdata.presentation.components.LocationInputRow
import ch.opentransportdata.presentation.components.LocationResultList
import ch.opentransportdata.presentation.theme.OJPAndroidSDKTheme
import kotlinx.coroutines.launch

/**
 * Created by Michael Ruppen on 25.04.2024
 */
@Composable
fun LirScreenComposable(
    navHostController: NavHostController,
    viewModel: LirViewModel = viewModel()
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
            modifier = Modifier
                .padding(innerPaddings)
                .fillMaxSize()
        ) {
            LocationInputRow(
                modifier = Modifier.padding(16.dp),
                requestLocation = { viewModel.getCurrentLocation() },
                textInputValue = state.value.inputValue,
                onTextValueChange = { viewModel.fetchLocations(it) },
                hasFocus = true,
                onFocusChange = {},
                onClearInputClicked = { viewModel.fetchLocations("") }
            )
            if (state.value.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .size(20.dp),
                    strokeWidth = 2.dp,
                )
            } else {
                LocationResultList(items = state.value.results, onLocationSelected = null)
            }
        }
    }

    state.value.events.forEach { event ->
        when (event) {
            is LirViewModel.Event.ShowSnackBar -> {
                coroutineScope.launch {
                    snackBarHostState.showSnackbar(message = event.message)
                }
            }
        }
        viewModel.eventHandled(event.id)
    }

}

fun AbstractPlaceDto.name(): String {
    return when (this) {
        is StopPlaceDto -> this.name?.text ?: ""
        is AddressDto -> this.name.text ?: ""
        else -> "undef"
    }
}

@PreviewLightDark
@Composable
private fun LirScreenPreview() {
    OJPAndroidSDKTheme {
        LirScreenComposable(navHostController = rememberNavController())
    }
}