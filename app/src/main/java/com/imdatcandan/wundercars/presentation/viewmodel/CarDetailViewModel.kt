package com.imdatcandan.wundercars.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imdatcandan.wundercars.common.Resource
import com.imdatcandan.wundercars.domain.CarUseCase
import com.imdatcandan.wundercars.presentation.navigation.Args
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CarDetailViewModel @Inject constructor(
    private val carUseCase: CarUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = mutableStateOf(CarDetailUiState())
    val uiState: State<CarDetailUiState> = _uiState

    init {
        savedStateHandle.get<String>(Args.ARG_CAR_ID)?.let {
            getCarDetail(it)
        }
    }

    fun getCarDetail(carId: String) {
        carUseCase.executeGetCarDetail(carId).onEach {
            when (it) {
                is Resource.Success -> _uiState.value = CarDetailUiState(carModel = it.data)
                is Resource.Error -> _uiState.value =
                    CarDetailUiState(error = it.exception.localizedMessage ?: "Error!")
                is Resource.Loading -> _uiState.value = CarDetailUiState(isLoading = true)
            }
        }.launchIn(viewModelScope)
    }


}