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
import ch.opentransportdata.presentation.lir.LirScreenComposable
import ch.opentransportdata.presentation.navigation.BottomAppBarWithNavigation
import ch.opentransportdata.presentation.navigation.BottomNavItem
import ch.opentransportdata.presentation.tir.TirScreenComposable
import ch.opentransportdata.presentation.theme.OJPAndroidSDKTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { OjpDemoApp() }
    }
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