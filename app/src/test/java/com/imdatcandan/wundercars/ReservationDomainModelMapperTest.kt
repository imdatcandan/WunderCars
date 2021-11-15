package com.imdatcandan.wundercars

import com.imdatcandan.wundercars.data.model.ReservationApiModel
import com.imdatcandan.wundercars.domain.mapper.ReservationDomainModelMapper
import com.imdatcandan.wundercars.domain.model.ReservationDomainModel
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertNotEquals
import org.junit.Test

@ExperimentalCoroutinesApi
class ReservationDomainModelMapperTest : BaseUnitTest<ReservationDomainModelMapper>() {

    override fun initSelf() = ReservationDomainModelMapper()

    @Test
    fun `should map ReservationApiModel to ReservationDomainModel`() {
        val result = tested.mapToModel(reservationApiModel)

        assertEquals(reservationDomainModel, result)
    }

    @Test
    fun `should map nullable ReservationApiModel to nonnullable ReservationDomainModel`() {
        val result = tested.mapToModel(nullReservationApiModel)

        assertEquals(mappedReservationDomainModel, result)
    }

    @Test
    fun `should not map ReservationApiModel to unexpected ReservationDomainModel`() {
        val result = tested.mapToModel(reservationApiModel)

        assertNotEquals(unExpectedReservationDomainModel, result)
    }

    private companion object {
        private val reservationApiModel = ReservationApiModel(
            carId = "946",
            reservationId = 0,
        )

        private val reservationDomainModel = ReservationDomainModel(
            carId = "946",
            reservationId = 0,
        )

        private val nullReservationApiModel = ReservationApiModel(
            carId = null,
            reservationId = null,
        )

        private val mappedReservationDomainModel = ReservationDomainModel(
            carId = "",
            reservationId = 0,
        )

        private val unExpectedReservationDomainModel = ReservationDomainModel(
            carId = "946",
            reservationId = 3,
        )
    }
}