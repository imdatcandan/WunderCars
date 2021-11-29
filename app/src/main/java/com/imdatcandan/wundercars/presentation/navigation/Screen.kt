package com.imdatcandan.wundercars.presentation.navigation

sealed class Screen(val route: String) {
    object CarMapScreen : Screen("car_map_screen")
    object CarDetailScreen : Screen("car_detail_screen")
    object LocationPermissionScreen : Screen("location_permission_screen")

}