package iti.mobile.barq.db

import iti.mobile.barq.model.WeatherForecast
import kotlinx.coroutines.flow.Flow

interface ILocalSource {
    suspend fun deleteCurrentWeather(weatherForecast: WeatherForecast): Int
    suspend fun insertCurrentWeather(weatherForecast: WeatherForecast): Long
    suspend fun getCurrentWeathers(): Flow<WeatherForecast>?
}