package com.imdatcandan.wundercars

import com.imdatcandan.wundercars.data.model.CarApiModel
import com.imdatcandan.wundercars.data.repository.CarRepositoryImpl
import com.imdatcandan.wundercars.data.service.CarService
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Test

@ExperimentalCoroutinesApi
class CarRepositoryTest : BaseUnitTest<CarRepositoryImpl>() {

    private val carService: CarService = mockk(relaxed = true)
    private val carApiModelList: List<CarApiModel> = mockk(relaxed = true)

    override fun initSelf() = CarRepositoryImpl(carService, coroutineTestRule.dispatcher)

    @Test
    fun `should get ad detail from service`() = testCoroutine {
        coEvery { carService.getCarList() } returns carApiModelList

        val result = tested.getCarList()

        assertEquals(result, carApiModelList)
    }

//    @Test
//    fun `should not get ad detail from service`() = testCoroutine {
//        coEvery { carService.getCarList() } returns car
//
//        val result = tested.getAdDetail(DEFAULT_AD_ID)
//
//        assertEquals(result, adApiModel)
//    }

}