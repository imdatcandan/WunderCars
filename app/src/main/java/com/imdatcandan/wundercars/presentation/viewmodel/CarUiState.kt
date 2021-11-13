package com.imdatcandan.wundercars.presentation.viewmodel

import com.imdatcandan.wundercars.domain.CarDomainModel

data class CarUiState(
    val isLoading: Boolean = false,
    val carModel: List<CarDomainModel>? = null,
    val error: String = "",
)