package ch.opentransportdata.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ch.opentransportdata.ojp.OjpSdk
import ch.opentransportdata.presentation.lir.LirScreenComposable
import ch.opentransportdata.presentation.navigation.*
import ch.opentransportdata.presentation.theme.OJPAndroidSDKTheme
import ch.opentransportdata.presentation.tir.TirScreenComposable

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { OjpDemoApp() }
    }

    @Composable
    fun OjpDemoApp() {
        val bottomNavigationItems = listOf(BottomNavItem.Lir, BottomNavItem.Tir)
        OJPAndroidSDKTheme {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()

//            val currentDestination = navBackStackEntry?.destination
            var selectedBottomNavItem by remember { mutableIntStateOf(0) }

            Scaffold(
                bottomBar = {
//                    BottomAppBarWithNavigation(navController = navController)
                    NavigationBar {
                        bottomNavigationItems.forEachIndexed { index, item ->
                            NavigationBarItem(
                                selected = selectedBottomNavItem == index,
                                onClick = {
                                    selectedBottomNavItem = index
                                    navController.navigate(item) {
                                        // Pop up to the start destination of the graph
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        // Avoid multiple copies of the same destination when reselecting the same item
                                        launchSingleTop = true
                                        // Restore state when reselecting a previously selected item
                                        restoreState = true
                                    }
                                },
                                icon = { Icon(imageVector = item.icon, contentDescription = item.label) }
                            )
                        }
                    }
                }
            ) {
                NavHost(
                    modifier = Modifier.padding(it),
                    navController = navController,
                    startDestination = BottomNavItem.Lir
                ) {
                    composable<BottomNavItem.Lir> { LirNavHost() }
                    composable<BottomNavItem.Tir> { TirNavHost() }
                }
            }
        }
    }

    @Composable
    private fun LirNavHost() {
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = LocationSearchMask) {
            composable<LocationSearchMask> { LirScreenComposable(navHostController = navController) }
        }
    }

    @Composable
    private fun TirNavHost() {
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = TripSearchMask) {
            composable<TripSearchMask> { TirScreenComposable(navHostController = navController) }
            composable<TripResults> {  } // todo: implement
            composable<TripDetails> {  } // todo: implement
        }
    }

    companion object {
        val ojpSdk = OjpSdk(
            baseUrl = "https://odpch-api.clients.liip.ch/",
            endpoint = "ojp20-beta",
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
