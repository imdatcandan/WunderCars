package com.imdatcandan.wundercars.domain.mapper

import com.imdatcandan.wundercars.common.ModelMapper
import com.imdatcandan.wundercars.data.model.ReservationApiModel
import com.imdatcandan.wundercars.domain.model.ReservationDomainModel

class ReservationDomainModelMapper : ModelMapper<ReservationApiModel?, ReservationDomainModel> {
    override fun mapToModel(data: ReservationApiModel?): ReservationDomainModel {
        return ReservationDomainModel(
            carId = data?.carId ?: "",
            reservationId = data?.reservationId ?: 0
        )
    }
}