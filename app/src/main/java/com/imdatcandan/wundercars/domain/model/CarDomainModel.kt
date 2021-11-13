package com.imdatcandan.wundercars.domain.model

data class CarDomainModel(
    val carId: String,
    val latitude: Double,
    val longitude: Double,
    val title: String,
    val reservationState: Int,
    val damageDescription: String,
    val vehicleTypeImageUrl: String
) {
    override fun toString(): String {
        return "title:$title \nlocation:$latitude $longitude \nreservationState: $reservationState \ndamageDescription:$damageDescription"
    }
}