package com.imdatcandan.wundercars.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.imdatcandan.wundercars.domain.CarDomainModel
import com.imdatcandan.wundercars.presentation.viewmodel.CarDetailViewModel

@Composable
fun CarDetailScreen(carModel: CarDomainModel, viewModel: CarDetailViewModel = hiltViewModel()) {

    Column(Modifier.fillMaxSize()) {
        Image(
            painter = rememberImagePainter(carModel.vehicleTypeImageUrl),
            contentScale = ContentScale.Fit,
            contentDescription = null
        )

        Text(text = carModel.title)
        Text(text = carModel.damageDescription)

    }

}

