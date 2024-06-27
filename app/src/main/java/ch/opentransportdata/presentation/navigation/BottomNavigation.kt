package ch.opentransportdata.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

/**
 * Created by Michael Ruppen on 25.04.2024
 */
@Serializable
sealed class BottomNavItem(@Contextual val icon: ImageVector, val label: String) {

    @Serializable
    data object Lir : BottomNavItem(
        icon = Icons.Default.LocationOn,
        label = "LIR"
    )

    @Serializable
    data object Tir : BottomNavItem(
        icon = Icons.Default.Search,
        label = "TIR"
    )
}