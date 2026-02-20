package ch.opentransportdata.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SwapCalls
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import ch.opentransportdata.R
import ch.opentransportdata.presentation.theme.OJPAndroidSDKTheme

/**
 * Created by Michael Ruppen on 12.07.2024
 */
@Composable
fun TripResultHeader(
    modifier: Modifier = Modifier,
    originName: String,
    destinationName: String,
    swapSearch: () -> Unit,
    onSettingsClick: () -> Unit,
) {

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        tonalElevation = 1.dp
    ) {
        Box {
            Column(modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)) {
                Text(
                    modifier = Modifier.padding(all = 8.dp),
                    text = originName,
                    style = MaterialTheme.typography.titleLarge
                )
                HorizontalDivider(modifier = Modifier.padding(start = 8.dp, end = 48.dp))
                Text(
                    modifier = Modifier.padding(all = 8.dp),
                    text = destinationName,
                    style = MaterialTheme.typography.titleLarge
                )
            }
            Row(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 8.dp),
            ) {
                OutlinedIconButton(
                    modifier = Modifier,
                    onClick = onSettingsClick,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_settings),
                        contentDescription = "Filter Option",
                    )
                }
                OutlinedIconButton(
                    onClick = swapSearch
                ) {
                    Icon(imageVector = Icons.Default.SwapCalls, contentDescription = null)
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun TripResultHeaderPreview() {
    OJPAndroidSDKTheme {
        TripResultHeader(
            originName = "Bern, Eigerplatz",
            destinationName = "Basel SBB",
            swapSearch = {},
            onSettingsClick = {}
        )
    }
}