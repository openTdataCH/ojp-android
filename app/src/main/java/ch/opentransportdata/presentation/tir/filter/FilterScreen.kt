package ch.opentransportdata.presentation.tir.filter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ch.opentransportdata.domain.VehicleOption

@Composable
fun FilterScreen(
    currentWalkingSpeed: Int,
    onSelectWalkingSpeed: (Int) -> Unit,
    isDirectConnection: Boolean,
    onCheckDirectConnection: () -> Unit,
    isFewerTransfers: Boolean,
    onCheckFewerTransfers: () -> Unit,
    vehicleOptions: List<VehicleOption>,
    onToggleVehicle: (String) -> Unit,
    vehicleSubOptions: List<VehicleOption>,
    onToggleSubVehicle: (String) -> Unit,
    minDistance: String,
    onMinDistanceChange: (String) -> Unit,
    maxDistance: String,
    onMaxDistanceChange: (String) -> Unit,
    minDuration: String,
    onMinDurationChange: (String) -> Unit,
    maxDuration: String,
    onMaxDurationChange: (String) -> Unit,
    isBikeTransport: Boolean,
    onCheckBikeTransport: () -> Unit,
) {
    val selectedWalkingSpeed = remember { mutableIntStateOf(currentWalkingSpeed) }
    val walkingSpeedOptions = listOf(50, 75, 100, 150, 200, 400)

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 12.dp)
    ) {
        Text(text = "Deviation from average walking speed in percent. (100% == average)", textAlign = TextAlign.Center)
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            walkingSpeedOptions.forEach { walkingSpeed ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    RadioButton(
                        modifier = Modifier.size(40.dp),
                        selected = selectedWalkingSpeed.intValue == walkingSpeed,
                        onClick = {
                            onSelectWalkingSpeed(walkingSpeed)
                            selectedWalkingSpeed.value = walkingSpeed
                        },
                    )
                    Text("${walkingSpeed}%")
                }
            }
        }
        OptionItem(
            text = "Only direct connections",
            isSelected = isDirectConnection,
            onClick = {
                onCheckDirectConnection()
            }
        )
        OptionItem(
            text = "Fewer transfers",
            enabled = !isDirectConnection,
            isSelected = isFewerTransfers && !isDirectConnection,
            onClick = {
                onCheckFewerTransfers()
            },
        )
        OptionItem(
            text = "Bike Transport",
            isSelected = isBikeTransport,
            onClick = {
                onCheckBikeTransport()
            }
        )
        Text("Select your travel modes")
        vehicleOptions.forEach { option ->
            OptionItem(
                text = option.vehicleType,
                isSelected = option.isSelected,
                onClick = { onToggleVehicle(option.vehicleType) }
            )
        }
        Text("Select your rail sub mode")
        vehicleSubOptions.forEach { option ->
            OptionItem(
                text = option.vehicleType,
                isSelected = if (vehicleOptions.first().isSelected) option.isSelected else false,
                enabled = vehicleOptions.first().isSelected,
                onClick = { onToggleSubVehicle(option.vehicleType) }
            )
        }
        Text("Select your distance")
        Column {
            DistanceItem(
                text = "Min Distance (meter)",
                distance = minDistance,
                onValueChange = { onMinDistanceChange(it) }
            )
            DistanceItem(
                text = "Max Distance (meter)",
                distance = maxDistance,
                onValueChange = { onMaxDistanceChange(it) }
            )
        }
        Text("Select your duration")
        Column {
            DistanceItem(
                text = "Min Duration (min)",
                distance = minDuration,
                onValueChange = { onMinDurationChange(it) }
            )
            DistanceItem(
                text = "Max Duration (min)",
                distance = maxDuration,
                onValueChange = { onMaxDurationChange(it) }
            )
        }
    }
}

@Composable
private fun OptionItem(
    text: String,
    isSelected: Boolean,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    ListItem(
        headlineContent = {
            Text(
                text = text,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        trailingContent = {
            Switch(
                checked = isSelected,
                onCheckedChange = { onClick() },
                enabled = enabled
            )
        }
    )
}

@Composable
private fun DistanceItem(
    text: String,
    distance: String,
    onValueChange: (String) -> Unit,
) {
    ListItem(
        headlineContent = {
            Text(
                text = text,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        trailingContent = {
            TextField(
                modifier = Modifier.fillMaxWidth(0.5f),
                value = distance,
                onValueChange = { onValueChange(it) },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )
        }
    )
}