package ch.opentransportdata.presentation.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

/**
 * Created by Michael Ruppen on 25.04.2024
 */
sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    data object Lir : BottomNavItem(
        route = "lir",
        icon = Icons.Default.LocationOn,
        label = "LIR"
    )

    data object Tir : BottomNavItem(
        route = "tir",
        icon = Icons.Default.Search,
        label = "TIR"
    )
}

@Composable
fun BottomAppBarWithNavigation(navController: NavHostController) {
    BottomAppBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavigationItem(
                navController = navController,
                bottomNavItem = BottomNavItem.Lir,
                isSelected = currentDestination?.hierarchy?.any { it.route == BottomNavItem.Lir.route } == true)
            NavigationItem(
                navController = navController,
                bottomNavItem = BottomNavItem.Tir,
                isSelected = currentDestination?.hierarchy?.any { it.route == BottomNavItem.Tir.route } == true)
        }

    }
}


@Composable
fun NavigationItem(
    navController: NavHostController,
    bottomNavItem: BottomNavItem,
    isSelected: Boolean
) {
    val selectedColor = if (isSelected) Color.Blue else LocalContentColor.current

    FilledIconButton(
        colors = IconButtonDefaults.filledIconButtonColors(
            containerColor = selectedColor.copy(alpha = 0.3f),
            contentColor = selectedColor
        ),
        onClick = {
            navController.navigate(bottomNavItem.route) {
                // Pop up to the start destination of the graph
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                // Avoid multiple copies of the same destination when reselecting the same item
                launchSingleTop = true
                // Restore state when reselecting a previously selected item
                restoreState = true
            }
        }
    ) {
        Icon(imageVector = bottomNavItem.icon, contentDescription = bottomNavItem.label)
    }
}