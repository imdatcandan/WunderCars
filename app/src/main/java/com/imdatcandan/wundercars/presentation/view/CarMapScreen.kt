package com.imdatcandan.wundercars.presentation.view

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.imdatcandan.wundercars.R
import com.imdatcandan.wundercars.domain.model.CarDomainModel
import com.imdatcandan.wundercars.presentation.navigation.Screen
import kotlinx.coroutines.launch


@ExperimentalPermissionsApi
@Composable
fun CarMapScreen(carModelList: List<CarDomainModel>, navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val mapView = rememberMapViewWithLifecycle()

        MapViewContainer(
            map = mapView,
            carModelList = carModelList,
            navController = navController
        )
    }
}

@Composable
fun rememberMapViewWithLifecycle(): MapView {
    val context = LocalContext.current
    val mapView = remember {
        MapView(context).apply {
            id = R.id.map
        }
    }

    val lifecycleObserver = rememberMapLifecycleObserver(mapView)
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    DisposableEffect(lifecycle) {
        lifecycle.addObserver(lifecycleObserver)
        onDispose {
            lifecycle.removeObserver(lifecycleObserver)
        }
    }

    return mapView
}

@Composable
fun rememberMapLifecycleObserver(mapView: MapView): LifecycleEventObserver =
    remember(mapView) {
        LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_CREATE -> mapView.onCreate(Bundle())
                Lifecycle.Event.ON_START -> mapView.onStart()
                Lifecycle.Event.ON_RESUME -> mapView.onResume()
                Lifecycle.Event.ON_PAUSE -> mapView.onPause()
                Lifecycle.Event.ON_STOP -> mapView.onStop()
                Lifecycle.Event.ON_DESTROY -> mapView.onDestroy()
                else -> throw IllegalStateException()
            }
        }
    }

@ExperimentalPermissionsApi
@Composable
private fun MapViewContainer(
    map: MapView,
    carModelList: List<CarDomainModel>,
    navController: NavController
) {
    val coroutineScope = rememberCoroutineScope()

    val context = LocalContext.current


    AndroidView({ map }) { mapView ->
        coroutineScope.launch {
            mapView.getMapAsync { googleMap ->

                if (ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    googleMap.isMyLocationEnabled = true
                }

                carModelList.forEach {
                    val carModel = it
                    val location = LatLng(carModel.latitude, carModel.longitude)

                    googleMap.animateCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            location,
                            MAP_DEFAULT_ZOOM_LEVEL
                        )
                    )

                    val markerOptions = createMarkerOptions(carModel)
                    googleMap.addMarker(markerOptions)
                }

                var clickCount = 0

                googleMap.setOnMarkerClickListener { selectedMarker ->
                    return@setOnMarkerClickListener if (clickCount == 0) {
                        val selectedCar = carModelList.first { selectedMarker.snippet == it.carId }
                        googleMap.clear()
                        googleMap.addMarker(createMarkerOptions(selectedCar))?.showInfoWindow()
                        clickCount++
                        false
                    } else {
                        clickCount = 0
                        navController.navigate(Screen.CarDetailScreen.route + "/${selectedMarker.snippet}")
                        true
                    }
                }
            }
        }
    }
}


private fun createMarkerOptions(carModel: CarDomainModel): MarkerOptions {
    val location = LatLng(carModel.latitude, carModel.longitude)
    return MarkerOptions()
        .position(location)
        .title(carModel.title)
        .snippet(carModel.carId)
}

private const val MAP_DEFAULT_ZOOM_LEVEL = 15f