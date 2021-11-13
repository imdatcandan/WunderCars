package com.imdatcandan.wundercars.data.model

import com.google.gson.annotations.SerializedName

data class CarApiModel(
    val carId: String?,
    @SerializedName("lat")
    val latitude: Double?,
    @SerializedName("lon")
    val longitude: Double?,
    val title: String?,
    val reservationState: Int?,
    val damageDescription: String?,
    val vehicleTypeImageUrl: String?
)