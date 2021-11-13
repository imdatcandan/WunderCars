package com.imdatcandan.wundercars.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.imdatcandan.wundercars.R
import com.imdatcandan.wundercars.domain.model.CarDomainModel
import com.imdatcandan.wundercars.presentation.viewmodel.CarDetailViewModel

@Composable
fun CarDetailScreen(carModel: CarDomainModel, viewModel: CarDetailViewModel = hiltViewModel()) {

    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = rememberImagePainter(carModel.vehicleTypeImageUrl),
            contentScale = ContentScale.Fit,
            contentDescription = null
        )


        Text(text = carModel.toString())

        Button(
            onClick = { viewModel.reserveCar(carModel.carId) },
        ) {
            Text(stringResource(id = R.string.reserve_car_button))
        }
    }
}


