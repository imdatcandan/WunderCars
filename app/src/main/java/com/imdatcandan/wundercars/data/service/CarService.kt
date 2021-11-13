package com.imdatcandan.wundercars.data.service

import com.imdatcandan.wundercars.data.model.CarApiModel
import retrofit2.http.GET
import retrofit2.http.Path

interface CarService {

    @GET("wunderfleet-recruiting-dev/cars.json")
    suspend fun getCarList(): List<CarApiModel>

    @GET("wunderfleet-recruiting-dev/cars/{carId}")
    suspend fun getCarDetail(@Path("carId") carId: String): CarApiModel
}