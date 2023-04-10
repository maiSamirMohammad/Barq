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


    suspend fun insertCurrentWeather(weatherForecast: WeatherForecast)

    suspend fun getStoredWeatherForecast():Flow<WeatherForecast>?

    //alerts
    suspend fun getAlertByCompositeKey(lat: Double, lon: Double, startDate: Long): Flow<Alert>


    suspend fun getAlerts(): Flow<List<Alert>>


    suspend fun insertAlert(alert: Alert): Long


    suspend fun deleteAlert(alert: Alert)
}