package com.imdatcandan.wundercars.di

import com.imdatcandan.wundercars.BuildConfig
import com.imdatcandan.wundercars.data.BasicAuthInterceptor
import com.imdatcandan.wundercars.data.repository.CarRepository
import com.imdatcandan.wundercars.data.repository.CarRepositoryImpl
import com.imdatcandan.wundercars.data.repository.ReservationRepository
import com.imdatcandan.wundercars.data.repository.ReservationRepositoryImpl
import com.imdatcandan.wundercars.data.service.CarService
import com.imdatcandan.wundercars.data.service.ReservationService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().also {
            it.level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    @Singleton
    fun provideBasicAuthInterceptor(): BasicAuthInterceptor = BasicAuthInterceptor()


    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    @Provides
    @Singleton
    @Named("Auth")
    fun provideAuthOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        basicAuthInterceptor: BasicAuthInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(basicAuthInterceptor)
        .build()

    @Provides
    @Singleton
    fun provideCarService(okHttpClient: OkHttpClient): CarService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_API_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CarService::class.java)
    }

    @Provides
    @Singleton
    fun provideReservationService(@Named("Auth") okHttpClient: OkHttpClient): ReservationService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_AUTH_API_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
            .create(ReservationService::class.java)
    }

    @Provides
    @Singleton
    fun provideCarRepository(carService: CarService): CarRepository {
        return CarRepositoryImpl(carService)
    }

    @Provides
    @Singleton
    fun provideReservationRepository(reservationService: ReservationService): ReservationRepository {
        return ReservationRepositoryImpl(reservationService)
    }
}