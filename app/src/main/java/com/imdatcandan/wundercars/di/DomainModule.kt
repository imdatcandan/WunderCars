package com.imdatcandan.wundercars.di

import com.imdatcandan.wundercars.data.repository.CarRepository
import com.imdatcandan.wundercars.data.repository.ReservationRepository
import com.imdatcandan.wundercars.domain.mapper.CarDomainModelMapper
import com.imdatcandan.wundercars.domain.mapper.ReservationDomainModelMapper
import com.imdatcandan.wundercars.domain.usecase.CarUseCase
import com.imdatcandan.wundercars.domain.usecase.ReservationUseCase
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


    @Provides
    @Singleton
    fun provideReservationDomainModelMapper(): ReservationDomainModelMapper {
        return ReservationDomainModelMapper()
    }

    @Provides
    @Singleton
    fun provideReservationUseCase(
        reservationRepository: ReservationRepository,
        reservationDomainModelMapper: ReservationDomainModelMapper
    ): ReservationUseCase {
        return ReservationUseCase(reservationRepository, reservationDomainModelMapper)
    }
}