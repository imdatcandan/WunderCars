package com.imdatcandan.wundercars.domain

data class CarDomainModel(
    val carId: String,
    val latitude: Double,
    val longitude: Double,
    val title: String,
    val reservationState: Int,
    val damageDescription: String,
    val vehicleTypeImageUrl: String
)