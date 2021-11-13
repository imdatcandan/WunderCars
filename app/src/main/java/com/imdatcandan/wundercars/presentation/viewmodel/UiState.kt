package com.imdatcandan.wundercars.presentation.viewmodel

data class UiState<out T : Any> (
    val isLoading: Boolean = false,
    val data: T? = null,
    val error: String = "",
)