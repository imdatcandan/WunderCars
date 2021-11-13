package com.imdatcandan.wundercars.presentation.view

import android.os.Bundle
import android.util.Log
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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.imdatcandan.wundercars.R
import com.imdatcandan.wundercars.domain.CarDomainModel
import com.imdatcandan.wundercars.presentation.navigation.Screen
import com.imdatcandan.wundercars.presentation.theme.isDarkTheme
import kotlinx.coroutines.launch


@Composable
fun CarListScreen(carModelList: List<CarDomainModel>, navController: NavController) {
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

@Composable
private fun MapViewContainer(
    map: MapView,
    carModelList: List<CarDomainModel>,
    navController: NavController
) {
    val coroutineScope = rememberCoroutineScope()

    AndroidView({ map }) { mapView ->
        coroutineScope.launch {
            mapView.getMapAsync { googleMap ->

                carModelList.forEach {
                    val carModel = it
                    val location = LatLng(it.latitude, it.longitude)

                    googleMap.animateCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            location,
                            MAP_DEFAULT_ZOOM_LEVEL
                        )
                    )

                    val markerOptions = MarkerOptions()
                        .position(location)
                        .title(carModel.title)
                        .snippet(carModel.carId)
                    googleMap.addMarker(markerOptions)

                    googleMap.setOnMarkerClickListener { selectedMarker ->

                        navController.navigate(Screen.CarDetailScreen.route + "/${selectedMarker.snippet}")
                        false
                    }

                }
                if (mapView.context.isDarkTheme()) {
                    try {
                        val style = MapStyleOptions.loadRawResourceStyle(
                            mapView.context,
                            R.raw.mapstyle_night
                        )

                        googleMap.setMapStyle(style)
                    } catch (exception: Exception) {
                        Log.e("Location", "Error occurred during applying map style: $exception")
                    }
                }

            }
        }
    }
}

private const val MAP_DEFAULT_ZOOM_LEVEL = 12f