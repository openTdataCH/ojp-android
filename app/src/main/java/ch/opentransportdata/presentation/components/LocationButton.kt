package ch.opentransportdata.presentation.components

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

/**
 * Created by Michael Ruppen on 25.04.2024
 */
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocationButton(
    modifier: Modifier = Modifier,
    requestLocation: () -> Unit,
) {
    val context = LocalContext.current
    var permissionsAlreadyRequested by rememberSaveable { mutableStateOf(false) }
    val locationPermissions = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        ),
        onPermissionsResult = { result ->
            permissionsAlreadyRequested = true
            val permissionsGranted = result.values.reduce { permission, isPermissionGranted ->
                Log.d("LocationButton","Permission: $permission, is granted: $isPermissionGranted")
                permission && isPermissionGranted
            }

            if (permissionsGranted) requestLocation()
        }
    )

    Button(
        modifier = modifier,
        onClick = {
            if (locationPermissions.allPermissionsGranted) {
                requestLocation()
            } else if (!permissionsAlreadyRequested && !locationPermissions.shouldShowRationale) {
                locationPermissions.launchMultiplePermissionRequest()
            } else if (locationPermissions.shouldShowRationale) {
                locationPermissions.launchMultiplePermissionRequest()
            } else {
                context.findActivity().openAppSettings()
            }
        }, content = {
            Icon(imageVector = Icons.Default.LocationOn, contentDescription = null)
        }
    )
}

fun Context.findActivity(): Activity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    throw IllegalStateException("no activity")
}

fun Activity.openAppSettings() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).also(::startActivity)
}