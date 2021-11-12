package com.imdatcandan.wundercars.data

import com.imdatcandan.wundercars.data.model.CarApiModel

interface CarRepository {
    suspend fun getCarList(): List<CarApiModel>
}