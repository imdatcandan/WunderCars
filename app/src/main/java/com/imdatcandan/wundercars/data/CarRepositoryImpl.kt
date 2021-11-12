package com.imdatcandan.wundercars.data

import com.imdatcandan.wundercars.data.model.CarApiModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CarRepositoryImpl(
    private val carService: CarService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): CarRepository {
    // As this operation is manually retrieving the cars from the server
    // using a blocking HttpURLConnection, it needs to move the execution
    // to an IO dispatcher to make it main-safe
    override suspend fun getCarList(): List<CarApiModel> = withContext(ioDispatcher) {
        return@withContext carService.getCarList()
    }
}
