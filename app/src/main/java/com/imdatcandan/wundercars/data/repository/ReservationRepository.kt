package com.imdatcandan.wundercars.data.repository

import com.imdatcandan.wundercars.data.model.ReservationApiModel

interface ReservationRepository {
    suspend fun reserveCar(carId: String): ReservationApiModel
}