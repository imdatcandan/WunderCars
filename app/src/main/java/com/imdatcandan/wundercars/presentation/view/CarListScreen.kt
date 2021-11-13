package com.imdatcandan.wundercars.presentation.view

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.imdatcandan.wundercars.domain.CarDomainModel

@Composable
fun CarListScreen(carModelList: List<CarDomainModel>) {

    LazyColumn {
        items(carModelList) { car ->
            Text(
                text = car.title
            )
        }
    }
}