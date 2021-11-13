package com.imdatcandan.wundercars.data.repository

import com.imdatcandan.wundercars.data.model.CarApiModel

interface CarRepository {
    suspend fun getCarList(): List<CarApiModel>
    suspend fun getCarDetail(carId: String): CarApiModel
}