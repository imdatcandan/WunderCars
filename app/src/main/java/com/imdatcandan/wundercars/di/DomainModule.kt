package com.imdatcandan.wundercars.di

import com.imdatcandan.wundercars.data.CarRepository
import com.imdatcandan.wundercars.domain.CarDomainModelMapper
import com.imdatcandan.wundercars.domain.CarUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun provideCarDomainModelMapper(): CarDomainModelMapper {
        return CarDomainModelMapper()
    }

    @Provides
    @Singleton
    fun provideCarUseCase(
        carRepository: CarRepository,
        carDomainModelMapper: CarDomainModelMapper
    ): CarUseCase {
        return CarUseCase(carRepository, carDomainModelMapper)
    }
}