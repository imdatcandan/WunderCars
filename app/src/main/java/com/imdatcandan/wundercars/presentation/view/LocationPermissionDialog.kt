package com.imdatcandan.wundercars.presentation.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState

@ExperimentalPermissionsApi
@Composable
fun LocationPermissionDialog(
    navigateToSettingsScreen: () -> Unit
) {
    // Track if the user doesn't want to see the rationale any more.
    var doNotShowRationale = remember { mutableStateOf(false) }

    // Camera permission state
    val locationPermissionState = rememberPermissionState(
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )

    when {
        // If the location permission is granted, then show screen with the feature enabled
        locationPermissionState.hasPermission -> {
            Text("Location permission Granted")
        }
        // If the user denied the permission but a rationale should be shown, or the user sees
        // the permission for the first time, explain why the feature is needed by the app and allow
        // the user to be presented with the permission again or to not see the rationale any more.
        locationPermissionState.shouldShowRationale ||
                !locationPermissionState.permissionRequested -> {
            if (doNotShowRationale.value) {
                Text("Feature not available")
            } else {
                Column {
                    Text("The location is important for this app. Please grant the permission.")
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        Button(onClick = { locationPermissionState.launchPermissionRequest() }) {
                            Text("Request permission")
                        }
                        Spacer(Modifier.width(8.dp))
                        Button(onClick = { doNotShowRationale.value = true }) {
                            Text("Don't show rationale again")
                        }
                    }
                }
            }
        }
        // If the criteria above hasn't been met, the user denied the permission. Let's present
        // the user with a FAQ in case they want to know more and send them to the Settings screen
        // to enable it the future there if they want to.
        else -> {
            Column {
                Text(
                    "Location permission denied. See this FAQ with information about why we " +
                            "need this permission. Please, grant us access on the Settings screen."
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = navigateToSettingsScreen) {
                    Text("Open Settings")
                }
            }
        }
    }
}