package com.imdatcandan.wundercars.domain.usecase

import com.imdatcandan.wundercars.common.Resource
import com.imdatcandan.wundercars.data.repository.ReservationRepository
import com.imdatcandan.wundercars.domain.mapper.ReservationDomainModelMapper
import com.imdatcandan.wundercars.domain.model.ReservationDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ReservationUseCase @Inject constructor(
    private val reservationRepository: ReservationRepository,
    private val reservationDomainModelMapper: ReservationDomainModelMapper
) {

    fun executeReserveCar(carId: String): Flow<Resource<ReservationDomainModel>> = flow {
        try {
            emit(Resource.Loading)
            val reservationApiModel = reservationRepository.reserveCar(carId)
            val reservationDomainModel =
                reservationDomainModelMapper.mapToModel(reservationApiModel)
            emit(Resource.Success(reservationDomainModel))
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }
}