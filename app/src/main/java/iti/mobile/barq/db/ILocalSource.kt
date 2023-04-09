package iti.mobile.barq.db

import iti.mobile.barq.model.WeatherForecast
import kotlinx.coroutines.flow.Flow

interface ILocalSource {

    suspend fun insertCurrentWeather(weatherForecast: WeatherForecast): Long
    suspend fun getStoredWeatherForecast(): Flow<WeatherForecast>?
}