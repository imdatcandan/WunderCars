package com.imdatcandan.wundercars.domain.usecase

import com.imdatcandan.wundercars.common.Resource
import com.imdatcandan.wundercars.data.repository.CarRepository
import com.imdatcandan.wundercars.domain.mapper.CarDomainModelMapper
import com.imdatcandan.wundercars.domain.model.CarDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CarUseCase @Inject constructor(
    private val carRepository: CarRepository,
    private val carDomainModelMapper: CarDomainModelMapper
) {

    fun executeGetCarList(): Flow<Resource<List<CarDomainModel>>> = flow {
        try {
            emit(Resource.Loading)
            val carApiModelList = carRepository.getCarList()
            val carDomainModelList = carApiModelList.map {
                carDomainModelMapper.mapToModel(it)
            }
            emit(Resource.Success(carDomainModelList))
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }

    fun executeGetCarDetail(carId: String): Flow<Resource<CarDomainModel>> = flow {
        try {
            emit(Resource.Loading)
            val carApiModel = carRepository.getCarDetail(carId)
            val carDomainModel = carDomainModelMapper.mapToModel(carApiModel)
            emit(Resource.Success(carDomainModel))
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }
}