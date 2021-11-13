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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.imdatcandan.wundercars.domain.CarUseCase
import com.imdatcandan.wundercars.presentation.theme.WunderCarsTheme
import com.imdatcandan.wundercars.presentation.view.CarListScreen
import com.imdatcandan.wundercars.presentation.view.ErrorRetryDialog
import com.imdatcandan.wundercars.presentation.viewmodel.CarViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity: ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WunderCarsTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    CarListPreview()
                }

            }
        }
    }
}

@Composable
fun CarListPreview(
    viewModel: CarViewModel = hiltViewModel()
) {

    val uiState = viewModel.uiState.value

    if (uiState.carModel != null) {
        CarListScreen(carModelList = uiState.carModel)
    }

    if (uiState.error.isNotBlank()) {
        ErrorRetryDialog(uiState.error)
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