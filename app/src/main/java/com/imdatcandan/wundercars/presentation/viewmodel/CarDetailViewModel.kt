package com.imdatcandan.wundercars.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imdatcandan.wundercars.common.Resource
import com.imdatcandan.wundercars.domain.model.CarDomainModel
import com.imdatcandan.wundercars.domain.model.ReservationDomainModel
import com.imdatcandan.wundercars.domain.usecase.CarUseCase
import com.imdatcandan.wundercars.domain.usecase.ReservationUseCase
import com.imdatcandan.wundercars.presentation.navigation.Args
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CarDetailViewModel @Inject constructor(
    private val carUseCase: CarUseCase,
    private val reservationUseCase: ReservationUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _carDetailUiState = mutableStateOf(UiState<CarDomainModel>())
    val carDetailUiState: State<UiState<CarDomainModel>> = _carDetailUiState

    private val _carReservationUiState = mutableStateOf(UiState<ReservationDomainModel>())
    val carReservationUiState: State<UiState<ReservationDomainModel>> = _carReservationUiState

    init {
        savedStateHandle.get<String>(Args.ARG_CAR_ID)?.let {
            getCarDetail(it)
        }
    }

    fun getCarDetail(carId: String) {
        carUseCase.executeGetCarDetail(carId).onEach {
            when (it) {
                is Resource.Success -> _carDetailUiState.value =
                    UiState(data = it.data)
                is Resource.Error -> _carDetailUiState.value =
                    UiState(error = it.exception.localizedMessage ?: "Error!")
                is Resource.Loading -> _carDetailUiState.value = UiState(isLoading = true)
            }
        }.launchIn(viewModelScope)
    }

    fun reserveCar(carId: String) {
        reservationUseCase.executeReserveCar(carId).onEach {
            when (it) {
                is Resource.Success -> _carReservationUiState.value =
                    UiState(data = it.data)
                is Resource.Error -> _carReservationUiState.value =
                    UiState(error = it.exception.localizedMessage ?: "Error!")
                is Resource.Loading -> _carReservationUiState.value =
                    UiState(isLoading = true)
            }
        }.launchIn(viewModelScope)
    }

}