package ch.opentransportdata.presentation.tir.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ch.opentransportdata.ojp.data.dto.response.GeoPositionDto
import org.maplibre.android.geometry.LatLng
import org.maplibre.android.maps.Style
import org.ramani.compose.CameraPosition
import org.ramani.compose.MapLibre
import org.ramani.compose.Polyline

/**
 * Created by Nico Brandenberger on 04.11.2025
 */

@Composable
fun MapScreen(
    coordinates: List<GeoPositionDto>,
    zoom: Double = 7.5
) {
    val styleUrl = "https://vectortiles.geo.admin.ch/styles/ch.swisstopo.basemap.vt/style.json"
    val mapLibrePoints = coordinates.map { LatLng(it.latitude, it.longitude) }
    val cameraPosition = rememberSaveable {
        mutableStateOf(
            CameraPosition(
                target = mapLibrePoints.first() ,
                zoom = zoom,
            )
        )
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Red
    ) {
        MapLibre(
            modifier = Modifier.fillMaxSize(),
            styleBuilder = Style.Builder().fromUri(styleUrl),
            cameraPosition = cameraPosition.value
        ) {
            Polyline(points = mapLibrePoints, color = "Red", lineWidth = 1.0F)
        }
    }
}