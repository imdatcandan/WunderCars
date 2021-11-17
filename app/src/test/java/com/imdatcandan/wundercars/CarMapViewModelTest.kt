package com.imdatcandan.wundercars

import com.imdatcandan.wundercars.common.Resource
import com.imdatcandan.wundercars.domain.model.CarDomainModel
import com.imdatcandan.wundercars.domain.usecase.CarUseCase
import com.imdatcandan.wundercars.presentation.viewmodel.CarMapViewModel
import com.imdatcandan.wundercars.presentation.viewmodel.UiState
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalCoroutinesApi
class CarMapViewModelTest : BaseUnitTest<CarMapViewModel>() {

    private val carUseCase: CarUseCase = mockk(relaxed = true)
    private val carDomainModelList: List<CarDomainModel> = mockk(relaxed = true)

    override fun initSelf() = CarMapViewModel(carUseCase)

    @ExperimentalTime
    @Test
    fun `test success car list ui state`() = testCoroutine {
        coEvery { carUseCase.executeGetCarList() } returns flowOf(Resource.Success(data = carDomainModelList))


        tested.getCarList()

        assertEquals(
            UiState(isLoading = false, data = carDomainModelList, error = ""),
            tested.uiState.value
        )
    }

    @ExperimentalTime
    @Test
    fun `test error car list ui state`() = testCoroutine {
        coEvery { carUseCase.executeGetCarList() } returns flowOf(
            Resource.Error(
                exception = Throwable(ERROR_MESSAGE)
            )
        )

        tested.getCarList()

        assertEquals(
            UiState(isLoading = false, data = null, error = ERROR_MESSAGE),
            tested.uiState.value
        )
    }

    companion object {
        private const val ERROR_MESSAGE = "error"
    }

}