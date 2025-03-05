package ch.opentransportdata.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import ch.opentransportdata.ojp.OjpSdk
import ch.opentransportdata.ojp.data.dto.response.PlaceResultDto
import ch.opentransportdata.presentation.lir.LirScreenComposable
import ch.opentransportdata.presentation.navigation.BottomNavItem
import ch.opentransportdata.presentation.navigation.LocationSearchMask
import ch.opentransportdata.presentation.navigation.TripResults
import ch.opentransportdata.presentation.navigation.TripSearchMask
import ch.opentransportdata.presentation.navigation.navtypes.PlaceResultType
import ch.opentransportdata.presentation.theme.OJPAndroidSDKTheme
import ch.opentransportdata.presentation.tir.TirScreenComposable
import ch.opentransportdata.presentation.tir.result.TripResultScreen
import kotlin.reflect.typeOf

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, true)
        setContent { OjpDemoApp() }
    }

    @Composable
    fun OjpDemoApp() {
        val bottomNavigationItems = listOf(BottomNavItem.Lir, BottomNavItem.Tir)
        OJPAndroidSDKTheme {
            val navController = rememberNavController()
//            val navBackStackEntry by navController.currentBackStackEntryAsState()
//            val currentDestination = navBackStackEntry?.destination
            var selectedBottomNavItem by remember { mutableIntStateOf(0) }

            Scaffold(
                contentWindowInsets = WindowInsets(0),
                bottomBar = {
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
            composable<TripResults>(
                typeMap = mapOf(
                    typeOf<PlaceResultDto?>() to PlaceResultType,
                    typeOf<PlaceResultDto>() to PlaceResultType,
                )
            ) { navBackstackEntry ->
                val parameters = navBackstackEntry.toRoute<TripResults>()
                TripResultScreen(
                    navHostController = navController,
//                    originName = parameters.origin.place.placeType?.name() ?: "-",
//                    viaName = parameters.via?.place?.placeType?.name(),
//                    destinationName = parameters.destination.place.placeType?.name() ?: "-"
                )
            }
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
