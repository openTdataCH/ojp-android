package ch.opentransportdata.presentation.feature.map

import android.Manifest
import android.location.Location
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ch.opentransportdata.ojp.domain.model.SharingCategory
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.gson.JsonPrimitive
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.maplibre.android.geometry.LatLng
import org.ramani.compose.CameraMotionType
import org.ramani.compose.CameraPosition
import org.ramani.compose.CenterState
import org.ramani.compose.Circle
import org.ramani.compose.MapLibre
import org.ramani.compose.MapStyle
import org.ramani.compose.rememberCameraPositionState
import kotlin.math.cos
import kotlin.math.pow

/**
 * Created by Deniz Kalem on 08.07.2026
 *
 */
@OptIn(ExperimentalPermissionsApi::class, FlowPreview::class)
@Composable
fun SharedMobilityMapScreen(
    viewModel: SharedMobilityMapViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val locationPermissions = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    )

    LaunchedEffect(Unit) {
        if (!locationPermissions.allPermissionsGranted) {
            locationPermissions.launchMultiplePermissionRequest()
        }
    }

    Scaffold(
        contentWindowInsets = WindowInsets(0),
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            if (locationPermissions.allPermissionsGranted) {
                SharedMobilityMap(
                    state = state,
                    onRegionChanged = viewModel::loadPois,
                    onPoiSelected = viewModel::selectPoi
                )
            } else {
                LocationPermissionRequest(
                    onRequestPermission = { locationPermissions.launchMultiplePermissionRequest() }
                )
            }

            CategoryFilterRow(
                selectedCategories = state.selectedCategories,
                onCategoryToggled = viewModel::toggleCategory,
                modifier = Modifier.align(Alignment.BottomCenter)
            )

            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            state.selectedPoi?.let { poi ->
                PoiDetailCard(
                    poi = poi,
                    onClose = viewModel::clearSelectedPoi,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .statusBarsPadding()
                )
            }
        }
    }

    LaunchedEffect(state.events) {
        state.events.forEach { event ->
            when (event) {
                is SharedMobilityMapViewModel.Event.ShowSnackBar -> coroutineScope.launch {
                    snackBarHostState.showSnackbar(message = event.message)
                }
            }
            viewModel.eventHandled(event.id)
        }
    }
}

@OptIn(FlowPreview::class)
@Composable
private fun SharedMobilityMap(
    state: SharedMobilityMapViewModel.UiState,
    onRegionChanged: (upperLeftLon: Double, upperLeftLat: Double, lowerRightLon: Double, lowerRightLat: Double) -> Unit,
    onPoiSelected: (String?) -> Unit
) {
    val cameraPositionState = rememberCameraPositionState(
        CameraPosition(target = BERN, zoom = DEFAULT_ZOOM)
    )
    val userLocation = remember { mutableStateOf(Location(PROVIDER_INITIAL)) }
    var hasCenteredOnUser by remember { mutableStateOf(false) }

    LaunchedEffect(userLocation.value) {
        val location = userLocation.value
        if (!hasCenteredOnUser && (location.latitude != 0.0 || location.longitude != 0.0)) {
            cameraPositionState.position = CameraPosition(
                target = LatLng(location.latitude, location.longitude),
                zoom = DEFAULT_ZOOM,
                motionType = CameraMotionType.EASE
            )
            hasCenteredOnUser = true
        }
    }

    LaunchedEffect(Unit) {
        snapshotFlowPosition(cameraPositionState)
            .distinctUntilChanged()
            .debounce(QUERY_DEBOUNCE_MS)
            .collect { (target, zoom) ->
                if (target != null && zoom != null && zoom >= MIN_QUERY_ZOOM) {
                    val (upperLeft, lowerRight) = boundingBox(target, zoom)
                    onRegionChanged(
                        upperLeft.longitude,
                        upperLeft.latitude,
                        lowerRight.longitude,
                        lowerRight.latitude
                    )
                }
            }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        MapLibre(
            modifier = Modifier.fillMaxSize(),
            style = MapStyle.Uri(STYLE_URL),
            cameraPositionState = cameraPositionState,
            userLocation = userLocation
        ) {
            state.visiblePois.forEach { poi ->
                androidx.compose.runtime.key(poi.id) {
                    val centerState = remember(poi.latitude, poi.longitude) {
                        CenterState(LatLng(poi.latitude, poi.longitude))
                    }
                    Circle(
                        centerState = centerState,
                        radius = 15f,
                        color = poi.category.markerColor(),
                        borderColor = "White",
                        borderWidth = 3f,
                        data = JsonPrimitive(poi.id),
                        onClick = { data -> onPoiSelected((data as? JsonPrimitive)?.asString) }
                    )
                }
            }
        }

        val zoomBy: (Double) -> Unit = { delta ->
            val current = cameraPositionState.position
            val target = current.target
            if (target != null) {
                cameraPositionState.position = CameraPosition(
                    target = target,
                    zoom = ((current.zoom ?: DEFAULT_ZOOM) + delta).coerceIn(MIN_ZOOM, MAX_ZOOM),
                    motionType = CameraMotionType.EASE
                )
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 80.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SmallFloatingActionButton(onClick = { zoomBy(1.0) }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Zoom in")
            }
            SmallFloatingActionButton(onClick = { zoomBy(-1.0) }) {
                Icon(imageVector = Icons.Default.Remove, contentDescription = "Zoom out")
            }
            FloatingActionButton(
                onClick = {
                    val location = userLocation.value
                    if (location.latitude != 0.0 || location.longitude != 0.0) {
                        cameraPositionState.position = CameraPosition(
                            target = LatLng(location.latitude, location.longitude),
                            zoom = DEFAULT_ZOOM,
                            motionType = CameraMotionType.EASE
                        )
                    }
                }
            ) {
                Icon(imageVector = Icons.Default.LocationOn, contentDescription = "Center on my location")
            }
        }
    }
}

@Composable
private fun CategoryFilterRow(
    selectedCategories: Set<SharingCategory>,
    onCategoryToggled: (SharingCategory) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 12.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SharingCategory.entries.forEach { category ->
            val selected = category in selectedCategories
            FilterChip(
                selected = selected,
                onClick = { onCategoryToggled(category) },
                label = { Text(category.displayName()) },
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    labelColor = MaterialTheme.colorScheme.onSurface,
                    selectedContainerColor = category.uiColor(),
                    selectedLabelColor = Color.White
                ),
                border = BorderStroke(
                    width = 1.dp,
                    color = if (selected) category.uiColor() else MaterialTheme.colorScheme.outline
                ),
                elevation = FilterChipDefaults.filterChipElevation(elevation = 3.dp)
            )
        }
    }
}

@Composable
private fun PoiDetailCard(
    poi: PoiMarker,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = poi.name.ifBlank { "POI" },
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = onClose) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                }
            }
            Text(
                text = poi.category?.displayName() ?: "Unbekannte Kategorie",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "%.5f, %.5f".format(poi.latitude, poi.longitude),
                style = MaterialTheme.typography.bodySmall
            )
            poi.additionalInformation.forEach { (key, value) ->
                Text(
                    text = "$key: $value",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
private fun LocationPermissionRequest(onRequestPermission: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Location permission is required to show the map at your current position.")
        FloatingActionButton(
            onClick = onRequestPermission,
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Icon(imageVector = Icons.Default.LocationOn, contentDescription = "Grant location permission")
        }
    }
}

private fun snapshotFlowPosition(cameraPositionState: org.ramani.compose.CameraPositionState) =
    androidx.compose.runtime.snapshotFlow { cameraPositionState.position }
        .map { position -> position.target to position.zoom }

private fun boundingBox(center: LatLng, zoom: Double): Pair<LatLng, LatLng> {
    val latSpan = 360.0 / 2.0.pow(zoom)
    val lonSpan = latSpan / cos(Math.toRadians(center.latitude)).coerceAtLeast(0.01)
    val upperLeft = LatLng(center.latitude + latSpan, center.longitude - lonSpan)
    val lowerRight = LatLng(center.latitude - latSpan, center.longitude + lonSpan)
    return upperLeft to lowerRight
}

private fun SharingCategory.displayName(): String = when (this) {
    SharingCategory.E_SCOOTER -> "E-Scooter"
    SharingCategory.BIKE -> "Bike"
    SharingCategory.CAR -> "Car"
    SharingCategory.CHARGING_STATION -> "Charging"
}

private fun SharingCategory.uiColor(): Color = when (this) {
    SharingCategory.E_SCOOTER -> Color(0xFFE53935)
    SharingCategory.BIKE -> Color(0xFF1E88E5)
    SharingCategory.CAR -> Color(0xFF212121)
    SharingCategory.CHARGING_STATION -> Color(0xFF43A047)
}

private fun SharingCategory?.markerColor(): String {
    val color = this?.uiColor() ?: Color(0xFFD81B60)
    return "#%06X".format(0xFFFFFF and color.toArgb())
}

private const val STYLE_URL =
    "https://vectortiles.geo.admin.ch/styles/ch.swisstopo.basemap.vt/style.json"
private const val PROVIDER_INITIAL = "initial"
private const val DEFAULT_ZOOM = 15.0
private const val MIN_ZOOM = 1.0
private const val MAX_ZOOM = 20.0
private const val MIN_QUERY_ZOOM = 11.0
private const val QUERY_DEBOUNCE_MS = 500L
private val BERN = LatLng(46.948, 7.4474)