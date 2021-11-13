package com.imdatcandan.wundercars.data

import com.imdatcandan.wundercars.data.model.CarApiModel
import com.imdatcandan.wundercars.domain.CarDomainModel

interface CarRepository {
    suspend fun getCarList(): List<CarApiModel>
}