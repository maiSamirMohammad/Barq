package iti.mobile.barq.model

import kotlinx.coroutines.flow.Flow

interface IRepository {
    //from network
    suspend fun getCurrentWeather(
        latitude: String,
        longitude: String,
        language: String ,
        unitOfMeasurement: String
    ): Flow<WeatherForecast>
}