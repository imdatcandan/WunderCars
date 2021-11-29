package com.imdatcandan.wundercars.presentation.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.imdatcandan.wundercars.R
import com.imdatcandan.wundercars.presentation.navigation.Args
import com.imdatcandan.wundercars.presentation.navigation.Screen
import com.imdatcandan.wundercars.presentation.theme.WunderCarsTheme
import com.imdatcandan.wundercars.presentation.viewmodel.CarDetailViewModel
import com.imdatcandan.wundercars.presentation.viewmodel.CarMapViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @ExperimentalPermissionsApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WunderCarsTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.CarMapScreen.route
                    ) {
                        composable(route = Screen.CarMapScreen.route) {
                            CarMapPreview(navController = navController)
                        }
                        composable(
                            route = Screen.CarDetailScreen.route + "/{${Args.ARG_CAR_ID}}"
                        ) {
                            CarDetailPreview()
                        }
                    }
                }
            }
        }
    }
}

@ExperimentalPermissionsApi
@Composable
fun CarMapPreview(
    viewModel: CarMapViewModel = hiltViewModel(),
    navController: NavController
) {

    val uiState = viewModel.uiState.value

    if (uiState.data != null) {
        CarMapScreen(carModelList = uiState.data, navController = navController)
    }

    if (uiState.error.isNotBlank()) {
        ErrorRetryDialog(uiState.error) {
            viewModel.getCarList()
        }
    }

    if (uiState.isLoading) {
        ProgressBar()
    }

}

@Composable
fun CarDetailPreview(viewModel: CarDetailViewModel = hiltViewModel()) {

    val carDetailUiState = viewModel.carDetailUiState.value
    val carReservationUiState = viewModel.carReservationUiState.value

    if (carDetailUiState.data != null) {
        CarDetailScreen(carModel = carDetailUiState.data)
    }

    if (carDetailUiState.error.isNotBlank()) {
        ErrorRetryDialog(carDetailUiState.error) {
            viewModel.getCarDetail(Args.ARG_CAR_ID)
        }
    }

    if (carDetailUiState.isLoading) {
        ProgressBar()
    }

    if (carReservationUiState.data != null) {
        val context = LocalContext.current
        Toast.makeText(
            context,
            stringResource(id = R.string.successful_reservation_toast),
            Toast.LENGTH_SHORT
        ).show()
    }

    if (carReservationUiState.error.isNotBlank()) {
        ErrorRetryDialog(carDetailUiState.error) {
            viewModel.getCarDetail(Args.ARG_CAR_ID)
        }
    }

    if (carReservationUiState.isLoading) {
        ProgressBar()
    }
}
