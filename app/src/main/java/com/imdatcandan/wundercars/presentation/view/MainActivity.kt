package com.imdatcandan.wundercars.presentation.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.imdatcandan.wundercars.presentation.navigation.Args
import com.imdatcandan.wundercars.presentation.navigation.Screen
import com.imdatcandan.wundercars.presentation.theme.WunderCarsTheme
import com.imdatcandan.wundercars.presentation.viewmodel.CarDetailViewModel
import com.imdatcandan.wundercars.presentation.viewmodel.CarViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


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

@Composable
fun CarMapPreview(
    viewModel: CarViewModel = hiltViewModel(),
    navController: NavController
) {

    val uiState = viewModel.uiState.value

    if (uiState.carModel != null) {
        CarListScreen(carModelList = uiState.carModel, navController = navController)
    }

    if (uiState.error.isNotBlank()) {
        ErrorRetryDialog(uiState.error) { _, _ ->
            viewModel.getCarList()
        }
    }

    if (uiState.isLoading) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun CarDetailPreview(viewModel: CarDetailViewModel = hiltViewModel()) {

    val uiState = viewModel.uiState.value

    if (uiState.carModel != null) {
        CarDetailScreen(carModel = uiState.carModel)
    }

    if (uiState.error.isNotBlank()) {
        ErrorRetryDialog(uiState.error) { _, _ ->
            viewModel.getCarDetail(Args.ARG_CAR_ID)
        }
    }

    if (uiState.isLoading) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
    }
}
