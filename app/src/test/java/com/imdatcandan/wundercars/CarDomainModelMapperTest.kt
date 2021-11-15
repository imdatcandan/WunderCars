package com.imdatcandan.wundercars


import com.imdatcandan.wundercars.data.model.CarApiModel
import com.imdatcandan.wundercars.domain.mapper.CarDomainModelMapper
import com.imdatcandan.wundercars.domain.model.CarDomainModel
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertNotEquals
import org.junit.Test

@ExperimentalCoroutinesApi
class CarDomainModelMapperTest : BaseUnitTest<CarDomainModelMapper>() {

    override fun initSelf() = CarDomainModelMapper()

    @Test
    fun `should map carApiModel to carDomainModel`() {
        val result = tested.mapToModel(carApiModel)

        assertEquals(carDomainModel, result)
    }

    @Test
    fun `should map nullable CarApiModel to nonnullable CarDomainModel`() {
        val result = tested.mapToModel(nullCarApiModel)

        assertEquals(mappedCarDomainModel, result)
    }

    @Test
    fun `should not map carApiModel to unexpected carDomainModel`() {
        val result = tested.mapToModel(carApiModel)

        assertNotEquals(unExpectedCarDomainModel, result)
    }

    private companion object {
        private val carApiModel = CarApiModel(
            carId = "5",
            title = "HILDE",
            latitude = 12.12,
            longitude = 13.13,
            reservationState = 1,
            damageDescription = "",
            vehicleTypeImageUrl = ""
        )

        private val carDomainModel = CarDomainModel(
            carId = "5",
            title = "HILDE",
            latitude = 12.12,
            longitude = 13.13,
            reservationState = 1,
            damageDescription = "",
            vehicleTypeImageUrl = ""
        )

        private val nullCarApiModel = CarApiModel(
            carId = null,
            title = "",
            latitude = null,
            longitude = null,
            reservationState = null,
            damageDescription = "",
            vehicleTypeImageUrl = ""
        )

        private val mappedCarDomainModel = CarDomainModel(
            carId = "",
            title = "",
            latitude = 0.0,
            longitude = 0.0,
            reservationState = 0,
            damageDescription = "",
            vehicleTypeImageUrl = ""
        )

        private val unExpectedCarDomainModel = CarDomainModel(
            carId = "3123",
            title = "TL",
            latitude = 12.12,
            longitude = 13.13,
            reservationState = 0,
            damageDescription = "",
            vehicleTypeImageUrl = ""
        )
    }
}