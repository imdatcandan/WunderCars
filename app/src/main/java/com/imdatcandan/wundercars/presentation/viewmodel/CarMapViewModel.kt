package com.imdatcandan.wundercars.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imdatcandan.wundercars.common.Resource
import com.imdatcandan.wundercars.domain.model.CarDomainModel
import com.imdatcandan.wundercars.domain.usecase.CarUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CarMapViewModel @Inject constructor(private val carUseCase: CarUseCase) : ViewModel() {

    private val _uiState = mutableStateOf(UiState<List<CarDomainModel>>())
    val uiState: State<UiState<List<CarDomainModel>>> = _uiState

    init {
        getCarList()
    }

    fun getCarList() {
        carUseCase.executeGetCarList().onEach {
            when (it) {
                is Resource.Success -> _uiState.value = UiState(data = it.data)
                is Resource.Error -> _uiState.value =
                    UiState(error = it.exception.localizedMessage ?: "Error!")
                is Resource.Loading -> _uiState.value = UiState(isLoading = true)
            }
        }.launchIn(viewModelScope)
    }


}