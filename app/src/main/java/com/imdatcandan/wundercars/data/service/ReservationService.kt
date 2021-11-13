package com.imdatcandan.wundercars.data.service

import com.imdatcandan.wundercars.data.model.ReservationApiModel
import retrofit2.http.Body
import retrofit2.http.POST

interface ReservationService {

    @POST("default/wunderfleet-recruiting-mobile-dev-quick-rental")
    suspend fun reserveCar(@Body requestBody: ReservationRequestBody): ReservationApiModel
}