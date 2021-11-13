package com.imdatcandan.wundercars.presentation.viewmodel

import com.imdatcandan.wundercars.domain.CarDomainModel

data class CarListUiState(
    val isLoading: Boolean = false,
    val carModel: List<CarDomainModel>? = null,
    val error: String = "",
)

data class CarDetailUiState(
    val isLoading: Boolean = false,
    val carModel: CarDomainModel? = null,
    val error: String = "",
)