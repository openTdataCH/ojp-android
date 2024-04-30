package ch.opentransportdata.presentation.tir

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ch.opentransportdata.presentation.theme.OJPAndroidSDKTheme

/**
 * Created by Michael Ruppen on 25.04.2024
 */
@Composable
fun TirScreenComposable(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Not yet implemented", style = MaterialTheme.typography.headlineMedium)
    }
}

@PreviewLightDark
@Composable
private fun TirScreenPreview() {
    OJPAndroidSDKTheme {
        TirScreenComposable(navHostController = rememberNavController())
    }
}