package com.imdatcandan.wundercars.domain.mapper

import com.imdatcandan.wundercars.common.ModelMapper
import com.imdatcandan.wundercars.data.model.CarApiModel
import com.imdatcandan.wundercars.domain.model.CarDomainModel

class CarDomainModelMapper : ModelMapper<CarApiModel?, CarDomainModel> {
    override fun mapToModel(data: CarApiModel?): CarDomainModel {
        return CarDomainModel(
            carId = data?.carId ?: "",
            latitude = data?.latitude ?: 0.0,
            longitude = data?.longitude ?: 0.0,
            title = data?.title ?: "",
            reservationState = data?.reservationState ?: 0,
            damageDescription = data?.damageDescription ?: "",
            vehicleTypeImageUrl = data?.vehicleTypeImageUrl ?: ""
        )
    }
}