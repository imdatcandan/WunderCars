package com.imdatcandan.wundercars.data

import com.imdatcandan.wundercars.data.model.CarApiModel
import com.imdatcandan.wundercars.domain.CarDomainModel
import retrofit2.http.GET

interface CarService {

    @GET("wunderfleet-recruiting-dev/cars.json")
    suspend fun getCarList(): List<CarApiModel>
}