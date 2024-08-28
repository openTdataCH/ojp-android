package ch.opentransportdata.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ch.opentransportdata.ojp.data.dto.response.PlaceResultDto
import ch.opentransportdata.presentation.lir.name

/**
 * Created by Michael Ruppen on 28.08.2024
 */
@Composable
fun LocationResultList(
    modifier: Modifier = Modifier,
    items: List<PlaceResultDto>,
    onLocationSelected: ((PlaceResultDto) -> Unit)?
) {

    LazyColumn(modifier = modifier) {
        items(
            items = items,
            key = { item -> item.place.name.text + item.place.position.longitude + item.place.position.latitude + item.distance }
        ) { item ->
            val listItemModifier = Modifier
            if (onLocationSelected != null) listItemModifier.then(Modifier.clickable { onLocationSelected(item) })

            ListItem(
                modifier = listItemModifier,
                headlineContent = {
                    Text(
                        text = item.place.placeType?.name() ?: "undef",
                        style = MaterialTheme.typography.titleSmall
                    )
                },
                supportingContent = {
                    Text(text = "Probability: ${item.probability}")
                }
            )
            if (items.last() != item) {
                HorizontalDivider(modifier = Modifier.padding(start = 16.dp))
            }
        }
    }
}