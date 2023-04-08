package iti.mobile.barq.network

import iti.mobile.barq.model.Constants
import iti.mobile.barq.model.WeatherForecast
import retrofit2.http.Query

interface IRemoteSource {
    suspend fun getCurrentWeather(
        latitude: String,
         longitude: String,
         language: String,
         unitOfMeasurement: String
    ): WeatherForecast
}