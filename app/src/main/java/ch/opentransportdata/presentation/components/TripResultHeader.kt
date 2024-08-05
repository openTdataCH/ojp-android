package ch.opentransportdata.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import ch.opentransportdata.presentation.theme.OJPAndroidSDKTheme

/**
 * Created by Michael Ruppen on 12.07.2024
 */
@Composable
fun TripResultHeader(
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