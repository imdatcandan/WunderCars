package com.imdatcandan.wundercars.di

import com.imdatcandan.wundercars.BuildConfig
import com.imdatcandan.wundercars.data.CarRepositoryImpl
import com.imdatcandan.wundercars.data.CarService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().also {
            it.level = HttpLoggingInterceptor.Level.BODY
        }


    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    @Provides
    @Singleton
    fun provideCarService(okHttpClient: OkHttpClient) : CarService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_API_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CarService::class.java)
    }


    @Provides
    @Singleton
    fun provideCarRepository(carService: CarService) : CarRepositoryImpl {
        return CarRepositoryImpl(carService)
    }
}