package ch.opentransportdata.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import ch.opentransportdata.ojp.data.dto.response.PlaceResultDto
import ch.opentransportdata.ojp.domain.model.PtMode
import ch.opentransportdata.presentation.lir.name
import ch.opentransportdata.presentation.theme.OJPAndroidSDKTheme
import ch.opentransportdata.presentation.tir.PreviewData

/**
 * Created by Michael Ruppen on 28.08.2024
 */
@Composable
fun LocationResultList(
    modifier: Modifier = Modifier,
    items: List<PlaceResultDto>,
    onLocationSelected: ((PlaceResultDto) -> Unit)?
) {
    val sortedItems = items.sortedByDescending { it.probability }
    LazyColumn(modifier = modifier) {
        items(
            items = sortedItems,
            key = { item -> item.place?.name?.text + item.place?.position?.longitude + item.place?.position?.latitude + item.distance }
        ) { item ->
            val listItemModifier = if (onLocationSelected != null) Modifier.clickable { onLocationSelected(item) } else Modifier

            ListItem(
                modifier = listItemModifier,
                leadingContent = {
                    Icon(imageVector = item.transportIcon(), contentDescription = null)
                },
                headlineContent = {
                    Text(
                        text = item.place?.placeType?.name() ?: "undef",
                        style = MaterialTheme.typography.titleSmall
                    )
                },
                supportingContent = {
                    Text(text = "Probability: ${item.probability}")
                }
            )
            if (sortedItems.last() != item) {
                HorizontalDivider(modifier = Modifier.padding(start = 16.dp))
            }
        }
    }
}

@Composable
private fun PlaceResultDto.transportIcon(): ImageVector {
    val modes = this.place?.mode
    return when {
        modes.isNullOrEmpty() -> Icons.Default.PinDrop
        modes.any { it.ptMode == PtMode.RAIL } -> Icons.Default.Train
        modes.any { it.ptMode == PtMode.BUS } -> Icons.Default.DirectionsBus
        modes.any { it.ptMode == PtMode.TRAM } -> Icons.Default.Tram
        modes.any { it.ptMode == PtMode.UNDERGROUND } -> Icons.Default.Subway
        modes.any { it.ptMode == PtMode.WATER } -> Icons.Default.DirectionsBoat
        modes.any { it.ptMode == PtMode.TELECABIN } -> Icons.Default.DirectionsRailway
        else -> Icons.Default.PinDrop
    }
}

@PreviewLightDark
@Composable
private fun LocationListPreview() {
    OJPAndroidSDKTheme {
        LocationResultList(
            items = listOf(
                PreviewData.boatLocation,
                PreviewData.busLocation,
                PreviewData.trainLocation,
                PreviewData.tramLocation
            ),
            onLocationSelected = null
        )
    }
}