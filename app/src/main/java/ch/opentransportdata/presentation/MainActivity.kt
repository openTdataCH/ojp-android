package ch.opentransportdata.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ch.opentransportdata.ojp.OjpSdk
import ch.opentransportdata.presentation.lir.LirScreenComposable
import ch.opentransportdata.presentation.navigation.BottomAppBarWithNavigation
import ch.opentransportdata.presentation.navigation.BottomNavItem
import ch.opentransportdata.presentation.theme.OJPAndroidSDKTheme
import ch.opentransportdata.presentation.tir.TirScreenComposable

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { OjpDemoApp() }
    }

    @Composable
    fun OjpDemoApp() {
        OJPAndroidSDKTheme {
            val navController = rememberNavController()

            Scaffold(
                bottomBar = { BottomAppBarWithNavigation(navController = navController) }
            ) {
                NavHost(
                    modifier = Modifier.padding(it),
                    navController = navController,
                    startDestination = BottomNavItem.Lir.route
                ) {
                    composable(BottomNavItem.Lir.route) { LirScreenComposable(navHostController = navController) }
                    composable(BottomNavItem.Tir.route) { TirScreenComposable(navHostController = navController) }
                }
            }
        }
    }

    companion object {
        val ojpSdk = OjpSdk(
            baseUrl = "https://odpch-api.clients.liip.ch/",
            endpoint = "ojp20-test",
            httpHeaders = hashMapOf(
                Pair(
                    "Authorization",
                    "Bearer eyJvcmciOiI2M2Q4ODhiMDNmZmRmODAwMDEzMDIwODkiLCJpZCI6IjUzYzAyNWI2ZTRhNjQyOTM4NzMxMDRjNTg2ODEzNTYyIiwiaCI6Im11cm11cjEyOCJ9"
                )
            ),
            requesterReference = "OJP_Demo",
        )
    }
}
