package com.imdatcandan.wundercars

import app.cash.turbine.test
import com.imdatcandan.wundercars.common.Resource
import com.imdatcandan.wundercars.data.model.CarApiModel
import com.imdatcandan.wundercars.data.repository.CarRepositoryImpl
import com.imdatcandan.wundercars.domain.mapper.CarDomainModelMapper
import com.imdatcandan.wundercars.domain.model.CarDomainModel
import com.imdatcandan.wundercars.domain.usecase.CarUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalCoroutinesApi
class CarUseCaseTest : BaseUnitTest<CarUseCase>() {

    private val carRepository: CarRepositoryImpl = mockk(relaxed = true)
    private val carApiModel: CarApiModel = mockk(relaxed = true)

    private val carDomainModel: CarDomainModel = mockk(relaxed = true)
    private val carDomainModelMapper: CarDomainModelMapper = mockk(relaxed = true)
    override fun initSelf() = CarUseCase(carRepository, carDomainModelMapper)

    @ExperimentalTime
    @Test
    fun `should execute get ad details successfully`() = testCoroutine {
        coEvery { carRepository.getCarList() } returns listOf(carApiModel)
        every { carDomainModelMapper.mapToModel(carApiModel) } returns carDomainModel

        val result = tested.executeGetCarList()

        result.test {
            assertEquals(Resource.Success(listOf(carDomainModel)), expectMostRecentItem())
        }
    }

    @ExperimentalTime
    @Test
    fun `should not execute get ad details`() = testCoroutine {
        val exception = Exception("test")
        coEvery { carRepository.getCarList() } throws exception

        val result = tested.executeGetCarList()

        result.test {
            assertEquals(Resource.Error(exception), expectMostRecentItem())
        }
    }
}