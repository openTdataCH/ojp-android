package ch.opentransportdata.presentation.feature.stopevent

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ch.opentransportdata.presentation.components.LocationInputRow
import ch.opentransportdata.presentation.components.LocationResultList
import ch.opentransportdata.presentation.navigation.StopEventResults
import ch.opentransportdata.presentation.theme.OJPAndroidSDKTheme
import kotlinx.coroutines.launch

/**
 * Created by Deniz Kalem on 20.05.2026
 */
@Composable
fun StopEventSearchScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    viewModel: StopEventSearchViewModel = viewModel(),
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val state = viewModel.state.collectAsState()

    Scaffold(
        contentWindowInsets = WindowInsets.statusBars,
        snackbarHost = {
            SnackbarHost(modifier = Modifier.imePadding(), hostState = snackBarHostState)
        }
    ) { innerPaddings ->
        Column(
            modifier = modifier
                .padding(innerPaddings)
                .fillMaxSize()
        ) {
            LocationInputRow(
                modifier = Modifier.padding(16.dp),
                isLocationButtonEnabled = false,
                requestLocation = {},
                textInputValue = state.value.inputValue,
                onTextValueChange = { viewModel.fetchLocations(it) },
                hasFocus = true,
                onFocusChange = {},
                onClearInputClicked = { viewModel.fetchLocations("") }
            )
            LocationResultList(
                items = state.value.results,
                onLocationSelected = { viewModel.onLocationSelected(it) }
            )
        }
    }

    state.value.events.forEach { event ->
        when (event) {
            is StopEventSearchViewModel.Event.ShowSnackBar -> {
                coroutineScope.launch {
                    snackBarHostState.showSnackbar(message = event.message)
                }
            }

            is StopEventSearchViewModel.Event.OpenResults -> {
                navHostController.navigate(StopEventResults(stop = event.location))
                viewModel.resetData()
            }
        }
        viewModel.eventHandled(event.id)
    }
}

@PreviewLightDark
@Composable
private fun StopEventSearchScreenPreview() {
    OJPAndroidSDKTheme {
        StopEventSearchScreen(navHostController = rememberNavController())
    }
}