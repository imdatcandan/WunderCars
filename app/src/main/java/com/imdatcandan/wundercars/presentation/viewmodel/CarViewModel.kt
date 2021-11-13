package com.imdatcandan.wundercars.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imdatcandan.wundercars.common.Resource
import com.imdatcandan.wundercars.domain.CarUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CarViewModel @Inject constructor(private val carUseCase: CarUseCase) : ViewModel() {

    private val _uiState = mutableStateOf(CarListUiState())
    val uiState: State<CarListUiState> = _uiState

    init {
        getCarList()
    }

    fun getCarList() {
        carUseCase.executeGetCarList().onEach {
            when (it) {
                is Resource.Success -> _uiState.value = CarListUiState(carModel = it.data)
                is Resource.Error -> _uiState.value = CarListUiState(error = it.exception.localizedMessage ?: "Error!")
                is Resource.Loading -> _uiState.value = CarListUiState(isLoading = true)
            }
        }.launchIn(viewModelScope)
    }


}