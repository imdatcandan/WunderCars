package com.imdatcandan.wundercars

import androidx.lifecycle.SavedStateHandle
import com.imdatcandan.wundercars.common.Resource
import com.imdatcandan.wundercars.domain.model.CarDomainModel
import com.imdatcandan.wundercars.domain.model.ReservationDomainModel
import com.imdatcandan.wundercars.domain.usecase.CarUseCase
import com.imdatcandan.wundercars.domain.usecase.ReservationUseCase
import com.imdatcandan.wundercars.presentation.navigation.Args
import com.imdatcandan.wundercars.presentation.viewmodel.CarDetailViewModel
import com.imdatcandan.wundercars.presentation.viewmodel.UiState
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalCoroutinesApi
class CarDetailViewModelTest : BaseUnitTest<CarDetailViewModel>() {

    private val carUseCase: CarUseCase = mockk(relaxed = true)
    private val reservationUseCase: ReservationUseCase = mockk(relaxed = true)

    private val carDomainModelList: List<CarDomainModel> = mockk(relaxed = true)
    private val carDomainModel: CarDomainModel = mockk(relaxed = true)
    private val reservationDomainModel: ReservationDomainModel = mockk(relaxed = true)
    private val savedStateHandle: SavedStateHandle = mockk(relaxed = true)

    override fun initSelf() :CarDetailViewModel {
        every { savedStateHandle.get<String>(Args.ARG_CAR_ID) } returns  DEFAULT_CAR_ID
        return CarDetailViewModel(carUseCase, reservationUseCase, savedStateHandle)
    }


    @ExperimentalTime
    @Test
    fun `test success car detail ui state`() = testCoroutine {

        every { carUseCase.executeGetCarDetail(DEFAULT_CAR_ID) } returns flowOf(
            Resource.Success(
                data = carDomainModel
            )
        )

        tested.getCarDetail(DEFAULT_CAR_ID)

        assertEquals(
            UiState(isLoading = false, data = carDomainModel, error = ""),
            tested.carDetailUiState.value
        )
    }

    @ExperimentalTime
    @Test
    fun `test error car detail ui state`() = testCoroutine {
        coEvery { carUseCase.executeGetCarDetail(DEFAULT_CAR_ID) } returns flowOf(
            Resource.Error(
                exception = Throwable(ERROR_MESSAGE)
            )
        )

        tested.getCarDetail(DEFAULT_CAR_ID)

        assertEquals(
            UiState(isLoading = false, data = null, error = ERROR_MESSAGE),
            tested.carDetailUiState.value
        )
    }


    @ExperimentalTime
    @Test
    fun `test success car reservation ui state`() = testCoroutine {
        coEvery { reservationUseCase.executeReserveCar(DEFAULT_CAR_ID) } returns flowOf(
            Resource.Success(
                data = reservationDomainModel
            )
        )


        tested.reserveCar(DEFAULT_CAR_ID)

        assertEquals(
            UiState(isLoading = false, data = reservationDomainModel, error = ""),
            tested.carReservationUiState.value
        )
    }

    @ExperimentalTime
    @Test
    fun `test error car reservation ui state`() = testCoroutine {
        coEvery { reservationUseCase.executeReserveCar(DEFAULT_CAR_ID) } returns flowOf(
            Resource.Error(
                exception = Throwable(ERROR_MESSAGE)
            )
        )

        tested.reserveCar(DEFAULT_CAR_ID)

        assertEquals(
            UiState(isLoading = false, data = null, error = ERROR_MESSAGE),
            tested.carReservationUiState.value
        )
    }

    companion object {
        const val DEFAULT_CAR_ID = "1"
        private const val ERROR_MESSAGE = "error"
    }

}