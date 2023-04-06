package iti.mobile.barq.model

import kotlinx.coroutines.flow.Flow

interface IRepository {
    //from network
    suspend fun getCurrentWeather(
        lat: String?,
        lon: String?,
        lang: String = Constants.LANGUAGE_ENGLISH,
        units: String = Constants.UNITS_DEFAULT
    ): Flow<WeatherForecast>
}