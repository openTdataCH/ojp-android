package ch.opentransportdata.presentation.tir

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import ch.opentransportdata.presentation.components.Label
import ch.opentransportdata.presentation.components.LabelType
import ch.opentransportdata.presentation.theme.OJPAndroidSDKTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by Michael Ruppen on 28.06.2024
 */
@Composable
fun TripItem(
    modifier: Modifier = Modifier,
    startWithWalkLeg: Boolean,
    hasDisruptions: Boolean,
    numberOfTransferLegs: Int
) {
    var isPriceVisible by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(key1 = Unit) {
        coroutineScope.launch {
            delay(2000)
            isPriceVisible = true
        }
    }

    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(all = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "07:30",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "08:54",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 2.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            if (startWithWalkLeg) {
                Text(
                    text = "07:48 ",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,

                    )
                Text(
                    text = "IC8 ab Brig",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,

                    )
                Spacer(modifier = Modifier.weight(1f))
            }
            Text(
                text = "1h 24m",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            HorizontalDivider(modifier = Modifier.align(Alignment.Center))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(numberOfTransferLegs) {
                    Surface(
                        modifier = Modifier.size(4.dp),
                        color = MaterialTheme.colorScheme.primary,
                        shape = CircleShape,
                        content = {}
                    )
                }
            }
        }

        Row(
            modifier = Modifier.padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "GL. 3",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = "1.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface

                )
                Icon(
                    modifier = Modifier.size(18.dp),
                    imageVector = Icons.Default.Person,
                    contentDescription = "first class occupancy"
                )

                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = "2.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Icon(
                    modifier = Modifier.size(18.dp),
                    imageVector = Icons.Default.Person,
                    contentDescription = "second class occupancy"
                )
            }
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                if (hasDisruptions) {
                    Label(
                        type = LabelType.RED,
                        icon = Icons.Default.Warning
                    )
                }
                AnimatedVisibility(visible = isPriceVisible) {
                    Label(
                        modifier = Modifier.padding(start = 8.dp),
                        type = LabelType.GREEN,
                        icon = Icons.Default.FavoriteBorder,
                        text = "ab CHF 25.20"
                    )
                }
            }
        }

    }
}

@PreviewLightDark
@Composable
private fun TripItemPreview() {
    OJPAndroidSDKTheme {
        TripItem(
            startWithWalkLeg = true,
            hasDisruptions = true,
            numberOfTransferLegs = 3
        )
    }
}

@PreviewLightDark
@Composable
private fun TripItemSecondPreview() {
    OJPAndroidSDKTheme {
        TripItem(
            startWithWalkLeg = false,
            hasDisruptions = false,
            numberOfTransferLegs = 0
        )
    }
}