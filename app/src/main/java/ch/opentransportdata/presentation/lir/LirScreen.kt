package ch.opentransportdata.presentation.lir

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
import ch.opentransportdata.presentation.components.LocationButton
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
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                LocationButton(requestLocation = { viewModel.getCurrentLocation() })
                OutlinedTextField(
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .fillMaxWidth(),
                    value = state.value.inputValue,
                    singleLine = true,
                    onValueChange = { viewModel.fetchLocations(it) },
                    placeholder = { Text(text = "Type to search") })
            }
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
                        key = { item -> item.place.name?.stationName + item.place.position?.longitude + item.place.position?.latitude + item.distance }
                    ) { item ->
                        ListItem(
                            headlineContent = {
                                Text(
                                    text = item.place.stopPlace?.name?.stationName ?: "undef",
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
            is LirViewModel.Event.ShowSnackBar -> {
                coroutineScope.launch {
                    snackBarHostState.showSnackbar(message = event.message)
                }
            }
        }
        viewModel.eventHandled(event.id)
    }

}

@PreviewLightDark
@Composable
private fun LirScreenPreview() {
    OJPAndroidSDKTheme {
        LirScreenComposable(navHostController = rememberNavController())
    }
}