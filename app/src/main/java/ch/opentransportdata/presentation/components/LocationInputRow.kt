package ch.opentransportdata.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import ch.opentransportdata.presentation.theme.OJPAndroidSDKTheme

/**
 * Created by Michael Ruppen on 10.06.2024
 */
@Composable
fun LocationInputRow(
    modifier: Modifier = Modifier,
    isLocationButtonEnabled: Boolean = true,
    requestLocation: () -> Unit,
    textInputValue: String,
    onTextValueChange: (String) -> Unit,
    supportingText: String? = null,
    hasFocus: Boolean = false,
    onFocusChange: () -> Unit,
    isLocationSelected: Boolean = false,
    onClearInputClicked: () -> Unit,
) {
    val focusRequester by remember { mutableStateOf(FocusRequester()) }
    LaunchedEffect(key1 = hasFocus) {
        if (hasFocus) focusRequester.requestFocus() else focusRequester.freeFocus()
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Top,
    ) {
        if (isLocationButtonEnabled) LocationButton(modifier = Modifier.padding(top = 4.dp), requestLocation = requestLocation)
        OutlinedTextField(
            modifier = Modifier
                .padding(start = 16.dp)
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .onFocusChanged { if (it.hasFocus && !hasFocus) onFocusChange() },
            value = textInputValue,
            singleLine = true,
            onValueChange = { onTextValueChange(it) },
            placeholder = { Text(text = "Type text") },
            trailingIcon = {
                IconButton(onClick = onClearInputClicked) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Clear input")
                }
            },
            supportingText = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    supportingText?.let {
                        Text(text = it)
                    }
                    if (isLocationSelected) {
                        Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = Color.Green)
                    }

                }
            },
        )
    }
}

@PreviewLightDark
@Composable
private fun InputRowPreview() {
    OJPAndroidSDKTheme {
        LocationInputRow(
            requestLocation = {},
            textInputValue = "Bern",
            onTextValueChange = {},
            hasFocus = false,
            onFocusChange = {},
            onClearInputClicked = {}
        )
    }
}