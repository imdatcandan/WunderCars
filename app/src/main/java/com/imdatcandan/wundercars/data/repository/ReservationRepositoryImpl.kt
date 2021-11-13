package com.imdatcandan.wundercars.data.repository

import com.imdatcandan.wundercars.data.model.ReservationApiModel
import com.imdatcandan.wundercars.data.service.ReservationRequestBody
import com.imdatcandan.wundercars.data.service.ReservationService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ReservationRepositoryImpl(
    private val reservationService: ReservationService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ReservationRepository {

    override suspend fun reserveCar(carId: String): ReservationApiModel = withContext(ioDispatcher) {
        return@withContext reservationService.reserveCar(ReservationRequestBody(carId))
    }
}
