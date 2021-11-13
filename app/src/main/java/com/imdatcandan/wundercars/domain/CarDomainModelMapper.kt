package com.imdatcandan.wundercars.domain

import com.imdatcandan.wundercars.common.ModelMapper
import com.imdatcandan.wundercars.data.model.CarApiModel

class CarDomainModelMapper : ModelMapper<CarApiModel?, CarDomainModel> {
    override fun mapToModel(data: CarApiModel?): CarDomainModel {
        return CarDomainModel(
            carId = data?.carId ?: "",
            latitude = data?.latitude ?: 0.0,
            longitude = data?.longitude ?: 0.0,
            title = data?.title ?: "No name",
            reservationState = data?.reservationState ?: 0,
            damageDescription = data?.damageDescription ?: "Unknown Damage",
            vehicleTypeImageUrl = data?.vehicleTypeImageUrl ?: ""
        )
    }
}